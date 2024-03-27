package com.algo.auth.ui;

import com.algo.alert.application.EmailService;
import com.algo.auth.domain.EmailCheck;
import com.algo.auth.domain.EmailCheckRepository;
import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.dto.ErrorRequest;
import com.algo.auth.dto.LoginRequest;
import com.algo.auth.dto.LoginResponse;
import com.algo.auth.dto.SignUpRequest;
import com.algo.auth.dto.SignUpResponse;
import com.algo.auth.infrastructure.JwtUtil;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/rest")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserInfoRepository userInfoRepository;
  private final EmailCheckRepository emailCheckRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;
  private final JwtUtil jwtUtil;

  /**
   * 로그인을 처리하는 POST 요청을 처리하는 메소드.
   *
   * @param req 로그인 요청에 필요한 정보를 담은 객체
   * @return 요청에 대한 ResponseEntity 객체.
   *         - 로그인이 성공하면 인증된 사용자의 이메일과 JWT 토큰을 포함한 ResponseEntity를 반환.
   *         - 잘못된 자격 증명이나 내부 인증 서비스 오류인 경우에는 적절한 상태 코드와 메시지를 포함한 ResponseEntity를 반환.
   */
  @ResponseBody
  @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
  public ResponseEntity login(@RequestBody LoginRequest req) {
    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
      String email = authentication.getName();
      UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmailAndIsActivateTrue(email);
      String token = jwtUtil.createToken(userInfoByEmail);
      LoginResponse loginResponse = new LoginResponse(email, token);
      return ResponseEntity.ok(loginResponse);
    } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ErrorRequest(HttpStatus.BAD_REQUEST, "계정 정보를 확인해 주세요."));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ErrorRequest(HttpStatus.BAD_REQUEST, "관리자에게 문의하여 주세요"));
    }
  }

  /**
   * 회원가입을 처리하는 메서드.
   *
   * <p>계정이 존재하는 경우 DB에서 계정을 확인하고 front에 응답.
   * 계정이 존재하지 않는 경우, 신규 계정 정보를 저장하고, 계정 정보를 기반으로 EmailCheck을 생성 및 저장한 후, 검증 URL을 이메일로 전송.
   *
   * @param req 회원가입 요청 정보를 담은 객체
   * @return 회원가입 처리 결과에 따른 응답 {@link ResponseEntity}
   */
  @ResponseBody
  @RequestMapping(value = "/auth/signup", method = RequestMethod.POST)
  public ResponseEntity signUp(@RequestBody SignUpRequest req) {
    UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmailAndIsActivateTrue(req.getEmail());
    if (Objects.nonNull(userInfoByEmail)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new ErrorRequest(HttpStatus.CONFLICT, "이미 존재하는 계정 입니다."));
    }

    UserInfo newUserInfo = req.convertToUserInfo(passwordEncoder);
    UserInfo savedUserInfo = userInfoRepository.save(newUserInfo);
    EmailCheck emailCheck = EmailCheck
        .builder()
        .checkId(UUID.nameUUIDFromBytes(req.getEmail().getBytes()).toString())
        .validateDate(LocalDateTime.now().plusMinutes(15L))
        .userInfo(savedUserInfo)
        .isExpire(false)
        .build();
    EmailCheck savedEmailCheck = emailCheckRepository.save(emailCheck);
    emailService.sendSignUpEamil(savedEmailCheck);

    //TODO : 사용자에게 전달할 안내 멘트는 프론트 쪽에서 작업할 수 있도록 하자. (프론트에는 상태코드만 전달하도록)
    return ResponseEntity
        .ok(new SignUpResponse(newUserInfo.getEmail(), "회원가입 신청이 완료되었습니다. 이메일을 확인해 주세요."));
  }

  /**
   * 이메일 확인을 위한 GET 요청을 처리하는 메소드
   *
   * @param token 이메일 확인을 위한 토큰
   * @return 요청에 대한 ResponseEntity 객체.
   *         - 이메일 확인이 성공하면 회원가입 완료 메시지를 포함한 ResponseEntity를 반환.
   *         - 유효하지 않은 토큰이거나 시간이 만료된 경우에는 적절한 상태 코드와 메시지를 포함한 ResponseEntity를 반환.
   */
  @ResponseBody
  @RequestMapping(value = "/auth/check-email/{token}", method = RequestMethod.GET)
  public ResponseEntity eamilCheck(@PathVariable String token) {
    EmailCheck emailCheck = emailCheckRepository.findById(token).orElse(null);
    if (emailCheck == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유효하지 않은 토큰입니다.");
    }

    UserInfo userInfo = emailCheck.getUserInfo();
    LocalDateTime validateDate = emailCheck.getValidateDate();
    LocalDateTime currentTime = LocalDateTime.now();

    if (currentTime.isAfter(validateDate)) {
      emailCheckRepository.deleteById(token);
      userInfoRepository.delete(userInfo);
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(new SignUpResponse(userInfo.getEmail(), "시간이 만료되었습니다."));
    }

    userInfo.activate();
    emailCheck.expire();
    userInfoRepository.save(userInfo);
    emailCheckRepository.save(emailCheck);

    return ResponseEntity
        .ok(new SignUpResponse(userInfo.getEmail(), "회원가입이 완료되었습니다 로그인해 주세요."));
  }
}