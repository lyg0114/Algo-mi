package com.algo.service;

import com.algo.model.dto.QuestionDto;
import com.algo.repository.querydsl.QuestionCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 18.11.23
 */
@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionCustomRepository questionCustomRepository;

  public Page<QuestionDto> findPaginatedForQuestions(QuestionDto questionDto, Pageable pageable) {
    return questionCustomRepository.findPaginatedForQuestions(questionDto, pageable);
  }
}
