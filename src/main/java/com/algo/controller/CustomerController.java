package com.algo.controller;

import com.algo.model.dto.PageResponseDto;
import com.algo.model.dto.QuestionDto;
import com.algo.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    PageResponseDto<QuestionDto> responseDto = PageResponseDto.of(
        questionService.findPaginatedForQuestions(questionDto, pageable)
    );
    model.addAttribute("contents", responseDto.getContent());
    model.addAttribute("totalPages", responseDto.getTotalPages());
    model.addAttribute("isStartNumberPeriod", responseDto.isStartNumberPeriod());
    model.addAttribute("isEndNumberPeriod", responseDto.isEndNumberPeriod());
    model.addAttribute("startNumber", responseDto.getStartNumber());
    model.addAttribute("endNumber", responseDto.getEndNumber());
    model.addAttribute("hasPrevious", responseDto.hasPrevious());
    model.addAttribute("hasNext", responseDto.hasNext());
    model.addAttribute("currentPage", responseDto.getNumber());
    return "customer/main-dashboard";
  }
}
