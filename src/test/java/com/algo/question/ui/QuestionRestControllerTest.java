package com.algo.question.ui;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.infrastructure.JwtUtil;
import com.algo.question.domain.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @package : com.algo.question.ui
 * @since : 30.03.24
 */
@ActiveProfiles("test")
@DisplayName("문제 CRUD REST-API 테스트")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuestionRestControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper mapper;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private UserInfoRepository userInfoRepository;
  @Autowired private PasswordEncoder encoder;

  @BeforeEach
  void init() {
    userInfoRepository.save(UserInfo
        .builder()
        .userId(1L)
        .userName("kyle")
        .email("user@example.com")
        .passwd(encoder.encode("password"))
        .role("USER").isActivate(true).build());
  }

  @DisplayName("문제 등록시 사용자 정보가 존재하지 않는 경우")
  @Test
  public void addQuestionWhenUserInformationDoesntExist() throws Exception {
    String token = jwtUtil.createToken(
        UserInfo.builder().email("noexist@example.com").role("USER").build());
    MvcResult mvcResult = mockMvc.perform(post("/api/questions")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(Question.builder()
                .title("sample-title")
                .fromSource("smaple-fromSource")
                .questionType("smaple-questionType")
                .content("smaple-content")
                .url("smaple-url")
                .build())))
        .andExpect(status().is4xxClientError())
        .andReturn();

    Map result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
    assertThat(result.get("status")).isEqualTo(400);
    assertThat(result.get("message")).isEqualTo("존재하지 않는 사용자 입니다.");
  }
}