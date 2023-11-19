package com.algo.controller;

import com.algo.model.Question;
import com.algo.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

  private final QuestionService questionService;

  @GetMapping("/main-dashboard")
  public String mainDashBoard(
      @PageableDefault Pageable pageable, Question owner, Model model
  ) {
    Page<Question> ownersResults = questionService.findPaginatedForQuestions(owner, pageable);
    List<Question> listOwners = ownersResults.getContent();
    model.addAttribute("currentPage", pageable.getPageNumber());
    model.addAttribute("totalPages", ownersResults.getTotalPages());
    model.addAttribute("totalItems", ownersResults.getTotalElements());
    model.addAttribute("listOwners", listOwners);
    return "customer/main-dashboard";
  }
}
