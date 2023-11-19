package com.algo.repository.querydsl;

import com.algo.model.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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

  public Page<Question> findAll(Question owner, Pageable pageable) {
    return null;
  }
}
