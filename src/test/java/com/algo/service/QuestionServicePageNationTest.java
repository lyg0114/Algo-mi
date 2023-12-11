package com.algo.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 27.11.23
 */
@SpringBootTest
@AutoConfigureMockMvc
class QuestionServicePageNationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void pagenation_attribuet_should_exist() throws Exception {
    mockMvc.perform(
            get("/customer/main-dashboard?page=2&size=5")
                .param("title", "DFS-Question")
        )
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("contents"))
        .andExpect(model().attributeExists("totalPages"))
        .andExpect(model().attributeExists("isStartNumberPeriod"))
        .andExpect(model().attributeExists("isEndNumberPeriod"))
        .andExpect(model().attributeExists("startNumber"))
        .andExpect(model().attributeExists("endNumber"))
        .andExpect(model().attributeExists("hasPrevious"))
        .andExpect(model().attributeExists("hasNext"))
        .andExpect(model().attributeExists("currentPage"))
        .andExpect(view().name("customer/main-dashboard"));
  }
}