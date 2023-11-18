package com.algo.service;

import com.algo.model.Question;
import com.algo.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  private final QuestionRepository questionRepository;

  public Page<Question> findPaginatedForQuestions(int page, int size, Question owner) {
    Pageable pageable = PageRequest.of(page - 1, size);
    return questionRepository.findAll(pageable);
  }
}
