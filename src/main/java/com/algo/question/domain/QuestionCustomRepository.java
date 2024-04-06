package com.algo.question.domain;


import static com.algo.question.domain.QQuestion.question;
import static com.algo.recovery.application.CreateRecoveryTargets.SCHEDULE;

import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository.querydsl
 * @since : 19.11.23
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestionCustomRepository {

  private final JPAQueryFactory queryFactory;
  private final ModelMapper modelMapper;

  public Page<QuestionResponse> findPaginatedForQuestions(QuestionRequest questionRequest, Pageable pageable) {
    questionRequest.bindSearchFrom();
    JPAQuery<Question> jpaQuery = findQuestions(questionRequest);
    long totalCount = getTotalCount(jpaQuery);
    return new PageImpl<>(
        getQuestionRequestList(jpaQuery, pageable),
        pageable,
        totalCount
    );
  }

  public JPAQuery<Question> findQuestions(QuestionRequest QuestionRequest) {
    return queryFactory
        .select(question)
        .from(question)
        .where(getConditionBuilder(QuestionRequest))
        .orderBy(question.createdDt.desc())
        ;
  }

  private static BooleanBuilder getConditionBuilder(QuestionRequest qReq) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (Objects.nonNull(qReq)) {
      if (StringUtils.isEmpty(qReq.getEmail())){ throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."); }
      if (Objects.nonNull(qReq.getFromDt()) && Objects.nonNull(qReq.getToDt())) { booleanBuilder.and(question.createdDt.between(qReq.getFromDt(), qReq.getToDt())); }
      if (!StringUtils.isEmpty(qReq.getTitle())) { booleanBuilder.or(question.title.like("%" + qReq.getTitle() + "%")); }
      if (!StringUtils.isEmpty(qReq.getFromSource())) { booleanBuilder.or(question.fromSource.like("%" + qReq.getFromSource() + "%")); }
      if (!StringUtils.isEmpty(qReq.getQuestionType())) { booleanBuilder.or( question.questionType.like("%" + qReq.getQuestionType() + "%")); }
      if (!StringUtils.isEmpty(qReq.getUrl())) { booleanBuilder.or(question.url.like("%" + qReq.getUrl() + "%")); }
      if (qReq.getReviewCount() > 0) { booleanBuilder.or(question.reviewCount.eq(qReq.getReviewCount())); }
      if (!qReq.getEmail().equals(SCHEDULE)) { booleanBuilder.and(question.userInfo.email.eq(qReq.getEmail())); }
    }
    return booleanBuilder;
  }

  private List<QuestionResponse> getQuestionRequestList(JPAQuery<Question> jpaQuery,
      Pageable pageable) {
    return jpaQuery
        .offset((long) (pageable.getPageNumber()) * pageable.getPageSize())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(i -> i.converToDto(modelMapper))
        .collect(Collectors.toList());
  }

  private long getTotalCount(JPAQuery<Question> questionsJPAQuery) {
    return questionsJPAQuery.fetch().size();
  }
}
