package com.algo.repository.querydsl;

import com.algo.model.dto.QuestionDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository.querydsl
 * @since : 19.11.23
 */
@Repository
@RequiredArgsConstructor
public class QuestionCustomRepository {

  private final JPAQueryFactory queryFactory;
  private final ModelMapper modelMapper;

  public Page<QuestionDto> findPaginatedForQuestions(QuestionDto questionDto, Pageable pageable) {
    return null;
  }
}
