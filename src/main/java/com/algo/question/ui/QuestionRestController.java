package com.algo.question.ui;

import com.algo.question.dto.QuestionRequest;
import com.algo.question.domain.Question;
import com.algo.question.application.QuestionService;
import com.algo.question.dto.QuestionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/question")
public class QuestionRestController {

  private final QuestionService questionService;
  private final ModelMapper modelMapper;

  @GetMapping("/{questionId}")
  public ResponseEntity<QuestionResponse> getQuestion(@PathVariable long questionId) {
    Question question = questionService.findQuestionById(questionId);
    if (question == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(
        question.converToDto(modelMapper), HttpStatus.OK)
        ;
  }

  @PostMapping
  public ResponseEntity<QuestionResponse> addQuestion(@RequestBody QuestionRequest QuestionRequest) {
    HttpHeaders headers = new HttpHeaders();
    QuestionResponse addQuestionResponse = questionService.addQuestion(QuestionRequest);
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
    QuestionResponse updateQuestionResponse = questionService.updateQuestion(questionId, QuestionRequest);
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
