package com.algo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;
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
  public void pagenation_attribute_should_exist() throws Exception {
    mockMvc.perform(get("/customer/main-dashboard"))
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

  @Test
  public void pagenation_first_page_test() throws Exception {
    mockMvc.perform(get("/customer/main-dashboard?page=0&size=10"))
        .andExpect(status().isOk())
        .andExpect(view().name("customer/main-dashboard"))
        .andDo(result -> {
          Map<String, Object> model = result.getModelAndView().getModel();
          assertThat(model.get("contents")).isNotNull();
          assertThat(model.get("totalPages")).isEqualTo(15);
          assertThat(model.get("isStartNumberPeriod")).isEqualTo(true);
          assertThat(model.get("isEndNumberPeriod")).isEqualTo(false);
          assertThat(model.get("startNumber")).isEqualTo(1);
          assertThat(model.get("endNumber")).isEqualTo(10);
          assertThat(model.get("hasPrevious")).isEqualTo(false);
          assertThat(model.get("hasNext")).isEqualTo(true);
          assertThat(model.get("currentPage")).isEqualTo(0);
        });
  }

  private static void printPageNationModel(Map<String, Object> model) {
    System.out.println("totalPages: " + model.get("totalPages"));
    System.out.println("isStartNumberPeriod: " + model.get("isStartNumberPeriod"));
    System.out.println("isEndNumberPeriod: " + model.get("isEndNumberPeriod"));
    System.out.println("startNumber: " + model.get("startNumber"));
    System.out.println("endNumber: " + model.get("endNumber"));
    System.out.println("hasPrevious: " + model.get("hasPrevious"));
    System.out.println("hasNext: " + model.get("hasNext"));
    System.out.println("currentPage: " + model.get("currentPage"));
  }
}