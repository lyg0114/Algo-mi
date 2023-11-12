package com.sportedu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sportedu.sporteduplatform.WithAdmin;
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
class AdminControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithAdmin
  void testAdminHtml() throws Exception {
    mockMvc.perform(get("/admin/main"))
        .andExpect(status().isOk())
        .andExpect(view().name("admin/main"));
  }
}