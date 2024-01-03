package com.algo.config.security.auth;

import com.algo.model.entity.UserInfo;
import com.algo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rest/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserInfoRepository userInfoRepository;
  private final JwtUtil jwtUtil;

  @ResponseBody
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity login(@RequestBody LoginReq loginReq) {

    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
      String email = authentication.getName();
      UserInfo userInfoByEmail = userInfoRepository.findUserInfoByEmail(email);
      String token = jwtUtil.createToken(userInfoByEmail);
      LoginRes loginRes = new LoginRes(email, token);
      return ResponseEntity.ok(loginRes);

    } catch (BadCredentialsException e) {
      ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception e) {
      ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
  }
}