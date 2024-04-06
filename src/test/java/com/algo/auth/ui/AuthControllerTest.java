package com.algo.auth.ui;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.algo.auth.domain.CheckEmail;
import com.algo.auth.domain.CheckEmailRepository;
import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.dto.LoginRequest;
import com.algo.auth.dto.SignUpRequest;
import com.algo.auth.infrastructure.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.auth.ui
 * @since : 06.01.24
 */
@ActiveProfiles("test")
@DisplayName("로그인 인증 REST-API 테스트")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private UserInfoRepository userInfoRepository;
  @Autowired private PasswordEncoder encoder;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private ObjectMapper mapper;
  @Autowired private CheckEmailRepository checkEmailRepository;

  private UserInfo userInfo = null;

  @BeforeEach
  void init() {
    userInfo = userInfoRepository.save(UserInfo
        .builder()
        .userId(1L)
        .userName("kyle")
        .email("user@example.com")
        .passwd(encoder.encode("password"))
        .role("USER").isActivate(true).build());
  }

  @DisplayName("로그인 성공")
  @Test
  public void testLoginSuccess() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/api/rest/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(LoginRequest.builder()
                .email("user@example.com")
                .password("password")
                .build())))
        .andExpect(status().isOk())
        .andReturn();
    Map result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
    Claims claims = jwtUtil.parseJwtClaims((String) result.get("token"));
    List roles = (List) claims.get("roles");
    assertThat((String) roles.get(0)).isEqualTo("USER");
    assertThat((String) claims.get("email")).isEqualTo("user@example.com");
    assertThat((String) claims.get("name")).isEqualTo("kyle");
  }

  @DisplayName("회원가입 성공")
  @Test
  public void testSignUpSuccess() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/api/rest/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(SignUpRequest.builder()
                .email("newUser@example.com")
                .userName("newUserName")
                .password("password")
                .build())))
        .andExpect(status().isOk())
        .andReturn();
    Map result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
    assertThat((String) result.get("email")).isEqualTo("newUser@example.com");
    assertThat((String) result.get("message")).isEqualTo("회원가입 신청이 완료되었습니다. 이메일을 확인해 주세요.");
  }

  @DisplayName("e-mail 체크 실패 : 유효한 토큰이 아닌 경우")
  @Test
  public void testInValidateEmailCheck() throws Exception {
    String inValidateToken = "in-validate-token";
    MvcResult mvcResult = mockMvc.perform(get("/api/rest/auth/check-email/" + inValidateToken))
        .andExpect(status().is4xxClientError())
        .andReturn();
    Map result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
    assertThat(result.get("status")).isEqualTo(400);
    assertThat((String) result.get("message")).isEqualTo("유효하지 않은 토큰입니다.");
  }

  @DisplayName("e-mail 체크 성공")
  @Test
  public void testValidateEmailCheck() throws Exception {
    String validateToken = "validate-token";
    checkEmailRepository.save(
        CheckEmail.builder()
            .checkId(validateToken)
            .validateDate(LocalDateTime.now().plusMinutes(15L))
            .userInfo(userInfo)
            .isExpire(false)
            .build()
    );
    MvcResult mvcResult = mockMvc.perform(get("/api/rest/auth/check-email/" + validateToken))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    Map result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
    assertThat((String) result.get("email")).isEqualTo("user@example.com");
    assertThat((String) result.get("message")).isEqualTo("회원가입이 완료되었습니다 로그인해 주세요.");
  }
}