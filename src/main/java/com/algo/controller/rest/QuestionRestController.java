package com.algo.controller.rest;

import com.algo.model.dto.QuestionDto;
import com.algo.model.entity.Question;
import com.algo.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @GetMapping("/test")
  @PreAuthorize("hasRole(@roles.ADMIN)")
  public String listQuestion() {
    System.out.println("###################################");
    System.out.println("###################################");
    System.out.println("###################################");
    System.out.println("###################################");
    return "SUCCESS";
  }

  @GetMapping("/{questionId}")
  @PreAuthorize("hasRole(@roles.USER)")
  public ResponseEntity<QuestionDto> getQuestion(@PathVariable long questionId) {
    Question question = questionService.findQuestionById(questionId);
    if (question == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(
        question.converToDto(modelMapper), HttpStatus.OK)
        ;
  }

  @PostMapping
  @PreAuthorize("hasRole(@roles.USER)")
  public ResponseEntity<QuestionDto> addQuestion(@RequestBody QuestionDto questionDto) {
    HttpHeaders headers = new HttpHeaders();
    QuestionDto addQuestionDto = questionService.addQuestion(questionDto);
    headers.setLocation(UriComponentsBuilder
        .newInstance()
        .path("/question/{id}")
        .buildAndExpand(addQuestionDto.getId())
        .toUri());
    return new ResponseEntity<>(addQuestionDto, headers, HttpStatus.CREATED);
  }

  @PutMapping("/{questionId}")
  @PreAuthorize("hasRole(@roles.USER)")
  public ResponseEntity<QuestionDto> updateQuestion(
      @PathVariable long questionId, @RequestBody QuestionDto questionDto
  ) {
    QuestionDto updateQuestionDto = questionService.updateQuestion(questionId, questionDto);
    if (updateQuestionDto == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(updateQuestionDto, HttpStatus.CREATED);
  }

  @DeleteMapping("/{questionId}")
  @PreAuthorize("hasRole(@roles.USER)")
  public ResponseEntity<QuestionDto> deleteQuestion(@PathVariable long questionId) {
    Question question = questionService.findQuestionById(questionId);
    if (question == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    questionService.deleteQuestion(questionId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
