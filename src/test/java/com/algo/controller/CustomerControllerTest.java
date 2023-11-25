package com.algo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.algo.mock.sample.CreateSampleData;
import com.algo.mock.security.WithUser;
import com.algo.model.entity.Question;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.controller
 * @since : 12.11.23
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;

  @BeforeEach
  void before() {
    questionRepository.deleteAll();
    userInfoRepository.deleteAll();
  }

  @Transactional
  @Test
  public void testSampleDataCreate() {
    Iterable<Question> questions = questionRepository.findAll();
    for (Question question : questions) {
      System.out.println("question.getTitle() = " + question.getTitle());
      System.out.println(
          "question.getUserInfo().getUserName() = " + question.getUserInfo().getUserName());
    }
  }

  @Test
  @WithUser
  void testMainDashBoardHtml() throws Exception {
    mockMvc.perform(
            get("/customer/main-dashboard?page=2&size=5")
                .param("title", "DFS-Question")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("customer/main-dashboard"));
  }
}