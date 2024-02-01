package com.algo.auth.ui;

import com.algo.auth.dto.ErrorRequest;
import com.algo.auth.infrastructure.JwtUtil;
import com.algo.auth.dto.LoginRequest;
import com.algo.auth.dto.LoginResponse;
import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@Controller
@RequestMapping("/rest/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserInfoRepository userInfoRepository;
  private final JwtUtil jwtUtil;

  @ResponseBody
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
      String email = authentication.getName();
      UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmail(email);
      String token = jwtUtil.createToken(userInfoByEmail);
      LoginResponse loginResponse = new LoginResponse(email, token);
      return ResponseEntity.ok(loginResponse);

    } catch (BadCredentialsException e) {
      ErrorRequest errorResponse = new ErrorRequest(HttpStatus.BAD_REQUEST, "Invalid username or password");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception e) {
      ErrorRequest errorResponse = new ErrorRequest(HttpStatus.BAD_REQUEST, e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
  }
}