package com.algo.auth.ui;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.dto.ErrorRequest;
import com.algo.auth.dto.LoginRequest;
import com.algo.auth.dto.LoginResponse;
import com.algo.auth.infrastructure.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserInfoRepository userInfoRepository;
  private final JwtUtil jwtUtil;

  @ResponseBody
  @RequestMapping(value = "/rest/auth/login", method = RequestMethod.POST)
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
}