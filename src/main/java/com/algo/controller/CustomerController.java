package com.algo.controller;

import com.algo.model.dto.QuestionDto;
import com.algo.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.controller
 * @since : 08.11.23
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

  private final QuestionService questionService;

  @GetMapping("/main-dashboard")
  public String mainDashBoard(
      @PageableDefault Pageable pageable, QuestionDto questionDto, Model model
  ) {
    Page<QuestionDto> paginatedQuestions = questionService
        .findPaginatedForQuestions(questionDto, pageable);
    List<QuestionDto> listQuestions = paginatedQuestions.getContent();
    model.addAttribute("currentPage", pageable.getPageNumber());
    model.addAttribute("totalPages", paginatedQuestions.getTotalPages());
    model.addAttribute("totalItems", paginatedQuestions.getTotalElements());
    model.addAttribute("listQuestions", listQuestions);
    return "customer/main-dashboard";
  }
}
