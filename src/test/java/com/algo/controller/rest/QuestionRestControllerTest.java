package com.algo.controller.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.algo.mock.security.WithMockCustomUser;
import com.algo.model.entity.Question;
import com.algo.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author : iyeong-gyo
 * @package : com.algo.controller.rest
 * @since : 02.12.23
 */
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class QuestionRestControllerTest {

  @MockBean
  private QuestionService questionService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockCustomUser
  void testGetQuestionSuccess() throws Exception {
    given(questionService.findQuestionById(1))
        .willReturn(Question.builder()
            .questionId(1L)
            .title("sample-title")
            .url("http://sample.url.com")
            .fromSource("leetcode")
            .reviewCount(5)
            .build()
        );
    this.mockMvc.perform(get("/question/1")
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("sample-title"))
        .andExpect(jsonPath("$.url").value("http://sample.url.com"))
        .andExpect(jsonPath("$.fromSource").value("leetcode"))
        .andExpect(jsonPath("$.reviewCount").value("5"))
    ;
  }
}