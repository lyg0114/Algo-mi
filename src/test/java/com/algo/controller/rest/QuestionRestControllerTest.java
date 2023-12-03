package com.algo.controller.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.algo.controller.rest.advice.ExceptionControllerAdvice;
import com.algo.mock.security.WithMockCustomUser;
import com.algo.model.dto.QuestionDto;
import com.algo.model.entity.Question;
import com.algo.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author : iyeong-gyo
 * @package : com.algo.controller.rest
 * @since : 02.12.23
 */
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class QuestionRestControllerTest {

  @Autowired
  private QuestionRestController questionRestController;
  @MockBean
  private QuestionService questionService;
  private MockMvc mockMvc;

  @BeforeEach
  void beforeEach() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(questionRestController)
        .setControllerAdvice(new ExceptionControllerAdvice())
        .build();
  }

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
    mockMvc.perform(get("/question/1")
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

  @Test
  @WithMockCustomUser
  void testGetQuestionNotFound() throws Exception {
    given(this.questionService.findQuestionById(2))
        .willReturn(null);
    mockMvc.perform(get("/question/2")
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
    ;
  }

  @Test
  @WithMockCustomUser
  void testCreateQuestionSuccess() throws Exception {
    QuestionDto newQuestionDto = QuestionDto.builder()
        .id(1L)
        .title("sample-title")
        .url("http://sample.url.com")
        .fromSource("leetcode")
        .reviewCount(5)
        .build();
    given(this.questionService.addQuestion(newQuestionDto))
        .willReturn(newQuestionDto);
    ObjectMapper mapper = new ObjectMapper();
    String newOwnerAsJSON = mapper.writeValueAsString(newQuestionDto);
    mockMvc.perform(post("/question")
            .content(newOwnerAsJSON)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.title").value("sample-title"))
        .andExpect(jsonPath("$.url").value("http://sample.url.com"))
        .andExpect(jsonPath("$.fromSource").value("leetcode"))
        .andExpect(jsonPath("$.reviewCount").value(5));
  }
}