package com.algo.controller;

import com.algo.model.Question;
import com.algo.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.controller
 * @since : 08.11.23
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

  private final QuestionService questionService;

  @GetMapping("/main-dashboard")
  public String mainDashBoard(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      Question owner, Model model
  ) {
    Page<Question> ownersResults = questionService.findPaginatedForQuestions(page, size, owner);
    List<Question> listOwners = ownersResults.getContent();
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", ownersResults.getTotalPages());
    model.addAttribute("totalItems", ownersResults.getTotalElements());
    model.addAttribute("listOwners", listOwners);
    return "customer/main-dashboard";
  }
}
