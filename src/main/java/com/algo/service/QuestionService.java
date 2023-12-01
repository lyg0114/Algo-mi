package com.algo.service;

import com.algo.model.dto.QuestionDto;
import com.algo.model.entity.Question;
import com.algo.repository.QuestionRepository;
import com.algo.repository.querydsl.QuestionCustomRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 18.11.23
 */
@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionCustomRepository questionCustomRepository;
  private final QuestionRepository questionRepository;
  private final ModelMapper modelMapper;


  @Transactional(readOnly = true)
  public Page<QuestionDto> findPaginatedForQuestions(QuestionDto questionDto, Pageable pageable) {
    return questionCustomRepository.findPaginatedForQuestions(questionDto, pageable);
  }

  @Transactional(readOnly = true)
  public Question findQuestionById(long questionId) {
    Question Queston = null;
    try {
      Queston = questionRepository.findById(questionId).orElseThrow();
    } catch (NoSuchElementException e) {
      return null;
    }
    return Queston;
  }

  @Transactional
  public QuestionDto addQuestion(Question question) {
    return questionRepository.save(question)
        .converToDto(modelMapper)
        ;
  }
}
