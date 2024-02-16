package com.algo.auth.ui;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.infrastructure.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author : iyeong-gyo
 * @package : com.algo.auth.ui
 * @since : 06.01.24
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private AuthenticationManager authenticationManager;
  @MockBean private UserInfoRepository userInfoRepository;
  @MockBean private JwtUtil jwtUtil;

//  @Test
//  public void testLoginSuccess() throws Exception {
//    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//        .username("test@example.com")
//        .password("password")
//        .roles("USER")
//        .build();
//
//    when(authenticationManager.authenticate(any()))
//        .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
//
//    when(userInfoRepository.findUserInfoByEmail(any()))
//        .thenReturn(UserInfo.builder()
//            .email("test@example.com")
//            .passwd("testPassword")
//            .build());
//
//    when(jwtUtil.createToken(any()))
//        .thenReturn("mockedToken");
//
//    MvcResult mvcResult = mockMvc.perform(post("/rest/auth/login")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content("{\"email\": \"test@example.com\", \"password\": \"password\"}"))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    String responseContent = mvcResult.getResponse().getContentAsString();
//    assertThat(responseContent)
//        .isEqualTo("{\"email\":\"test@example.com\",\"token\":\"mockedToken\"}");
//  }

//  @Test
//  public void testLoginFailure() throws Exception {
//  }
}