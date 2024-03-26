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

  @ResponseBody
  @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
  public ResponseEntity login(@RequestBody LoginRequest req) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
      String email = authentication.getName();
      UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmail(email);
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

  /*
    - 계정이 존재할 경우
      - DB에서 계정 확인후 front에 응답
    - 계정이 존재하지 않을 경우.
      - 신규 계정정보 저장.
      - 계정정보 기반으로 EmailCheck 생성 및 저장.
      - 검증 url을 e-mail로 전송.
   */
  @ResponseBody
  @RequestMapping(value = "/auth/signup", method = RequestMethod.POST)
  public ResponseEntity signUp(@RequestBody SignUpRequest req) {
    UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmail(req.getEmail());
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
        .build();
    EmailCheck savedEmailCheck = emailCheckRepository.save(emailCheck);
    emailService.sendSignUpEamil(savedEmailCheck);

    return ResponseEntity
        .ok("회원가입 신청이 완료되었습니다. 이메일을 확인해 주세요.");
  }

  @ResponseBody
  @RequestMapping(value = "/auth/check-email/{uuid}", method = RequestMethod.GET)
  public ResponseEntity eamilCheck(@PathVariable String uuid) {
    //1. 사용자로부터 받은 요청이 유효한지 체크
    //2. 해당 아이디로부터 token이 유효한지 확인
    //3-1. 유효하다면 회원가입 완료
    //3-2. 유효하지 않다면 DB에서 사용자정보 제거하고, 메일 재 전송 (유효시간이 지났습니다.)
    return ResponseEntity.ok("");
  }
}