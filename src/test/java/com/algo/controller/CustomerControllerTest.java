package com.algo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.algo.mock.security.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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

  @Test
  @WithMockCustomUser
  void testMainDashBoardHtml() throws Exception {
    mockMvc.perform(
            get("/customer/main-dashboard?page=2&size=5")
                .param("title", "DFS-Question")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("customer/main-dashboard"));
  }
}