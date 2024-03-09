package com.algo.question.ui;

import com.algo.auth.infrastructure.JwtUtil;
import com.algo.question.application.QuestionService;
import com.algo.question.domain.Question;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author : iyeong-gyo
 * @package : com.algo.controller.rest
 * @since : 01.12.23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionRestController {

  private final JwtUtil jwtUtil;
  private final QuestionService questionService;
  private final ModelMapper modelMapper;

  @GetMapping
  public ResponseEntity<Page<QuestionResponse>> getQuestions(
      QuestionRequest request, @PageableDefault(size = 12) Pageable pageable
  ) {
    Page<QuestionResponse> questions = questionService.findPaginatedForQuestions(request, pageable);
    if (questions != null && !questions.isEmpty()) {
      return new ResponseEntity<>(questions, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/{questionId}")
  public ResponseEntity<QuestionResponse> getQuestion(@PathVariable long questionId) {
    Question question = questionService.findQuestionById(questionId);
    if (question != null) {
      return new ResponseEntity<>(
          question.converToDto(modelMapper), HttpStatus.OK)
          ;
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public ResponseEntity<QuestionResponse> addQuestion(
      HttpServletRequest request, @RequestBody QuestionRequest QuestionRequest
  ) {
    HttpHeaders headers = new HttpHeaders();
    String email = jwtUtil.getEmail(request);
    QuestionResponse addQuestionResponse = questionService.addQuestion(email, QuestionRequest);
    headers.setLocation(UriComponentsBuilder
        .newInstance()
        .path("/question/{id}")
        .buildAndExpand(addQuestionResponse.getId())
        .toUri());
    return new ResponseEntity<>(addQuestionResponse, headers, HttpStatus.CREATED);
  }

  @PutMapping("/{questionId}")
  public ResponseEntity<QuestionResponse> updateQuestion(
      @PathVariable long questionId, @RequestBody QuestionRequest QuestionRequest
  ) {
    QuestionResponse updateQuestionResponse = questionService.updateQuestion(questionId,
        QuestionRequest);
    if (updateQuestionResponse == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(updateQuestionResponse, HttpStatus.CREATED);
  }

  @DeleteMapping("/{questionId}")
  public ResponseEntity<QuestionResponse> deleteQuestion(@PathVariable long questionId) {
    Question question = questionService.findQuestionById(questionId);
    if (question == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    questionService.deleteQuestion(questionId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
