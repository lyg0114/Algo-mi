package com.algo.auth.ui;

import com.algo.alert.application.EmailService;
import com.algo.auth.domain.CheckEmail;
import com.algo.auth.domain.CheckEmailRepository;
import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.dto.CheckEmailResponse;
import com.algo.auth.dto.LoginRequest;
import com.algo.auth.dto.LoginResponse;
import com.algo.auth.dto.SignUpRequest;
import com.algo.auth.dto.SignUpResponse;
import com.algo.auth.infrastructure.JwtUtil;
import com.algo.exception.dto.ExceptionResponse;
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
  private final CheckEmailRepository checkEmailRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;
  private final JwtUtil jwtUtil;

  /**
   * 로그인을 처리하는 POST 요청을 처리하는 메소드.
   *
   * @param loginRequest 로그인 요청에 필요한 정보를 담은 객체
   * @return 요청에 대한 ResponseEntity 객체.
   *      - 로그인이 성공하면 인증된 사용자의 이메일과 JWT 토큰을 포함한 ResponseEntity를 반환.
   *      - 잘못된 자격 증명이나 내부 인증 서비스 오류인 경우에는 적절한 상태 코드와 메시지를 포함한 ResponseEntity를 반환.
   */
  @ResponseBody
  @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
  public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
              loginRequest.getPassword()));
      String email = authentication.getName();
      UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmailAndIsActivateTrue(email);
      String token = jwtUtil.createToken(userInfoByEmail);
      LoginResponse loginResponse = new LoginResponse(email, token);
      return ResponseEntity.ok(loginResponse);
    } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "계정 정보를 확인해 주세요."));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "관리자에게 문의하여 주세요"));
    }
  }

  /**
   * 회원가입을 처리하는 메서드.
   *
   * <p>계정이 존재하는 경우 DB에서 계정을 확인하고 front에 응답.
   * 계정이 존재하지 않는 경우, 신규 계정 정보를 저장하고, 계정 정보를 기반으로 EmailCheck을 생성 및 저장한 후, 검증 URL을 이메일로 전송.
   *
   * @param signUpRequest 회원가입 요청 정보를 담은 객체
   * @return 회원가입 처리 결과에 따른 응답 {@link ResponseEntity}
   */
  @ResponseBody
  @RequestMapping(value = "/auth/signup", method = RequestMethod.POST)
  public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest) {
    UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmailAndIsActivateTrue(
        signUpRequest.getEmail());
    if (Objects.nonNull(userInfoByEmail)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new ExceptionResponse(HttpStatus.CONFLICT.value(), "이미 존재하는 계정 입니다."));
    }

    UserInfo newUserInfo = signUpRequest.convertToUserInfo(passwordEncoder);
    UserInfo savedUserInfo = userInfoRepository.save(newUserInfo);
    CheckEmail checkEmail = CheckEmail
        .builder()
        .checkId(UUID.nameUUIDFromBytes(signUpRequest.getEmail().getBytes()).toString())
        .validateDate(LocalDateTime.now().plusMinutes(15L))
        .userInfo(savedUserInfo)
        .isExpire(false)
        .build();
    CheckEmail savedCheckEmail = checkEmailRepository.save(checkEmail);
    emailService.sendSignUpEamil(savedCheckEmail);
    return ResponseEntity
        .ok(new SignUpResponse(newUserInfo.getEmail(), "회원가입 신청이 완료되었습니다. 이메일을 확인해 주세요."));
  }

  /**
   * 이메일 확인을 위한 GET 요청을 처리하는 메소드
   *
   * @param token 이메일 확인을 위한 토큰
   * @return 요청에 대한 ResponseEntity 객체.
   *      - 이메일 확인이 성공하면 회원가입 완료 메시지를 포함한 ResponseEntity를 반환.
   *      - 유효하지 않은 토큰이거나 시간이 만료된 경우에는 적절한 상태 코드와 메시지를 포함한 ResponseEntity를 반환.
   */
  @ResponseBody
  @RequestMapping(value = "/auth/check-email/{token}", method = RequestMethod.GET)
  public ResponseEntity checkEmail(@PathVariable String token) {
    CheckEmail checkEmail = checkEmailRepository.findById(token)
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
    UserInfo userInfo = checkEmail.getUserInfo();
    LocalDateTime validateDate = checkEmail.getValidateDate();
    LocalDateTime currentTime = LocalDateTime.now();

    if (currentTime.isAfter(validateDate)) {
      checkEmailRepository.deleteById(token);
      userInfoRepository.delete(userInfo);
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(new SignUpResponse(userInfo.getEmail(), "시간이 만료되었습니다."));
    }

    userInfo.activate();
    checkEmail.expire();
    userInfoRepository.save(userInfo);
    checkEmailRepository.save(checkEmail);

    return ResponseEntity
        .ok(new SignUpResponse(userInfo.getEmail(), "회원가입이 완료되었습니다 로그인해 주세요."));
  }
}