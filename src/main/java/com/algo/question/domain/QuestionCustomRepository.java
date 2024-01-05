package com.algo.question.domain;


import static com.algo.question.domain.QQuestion.question;

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

  public Page<QuestionResponse> findPaginatedForQuestions(QuestionRequest QuestionRequest,
      Pageable pageable) {
    JPAQuery<Question> jpaQuery = findQuestions(QuestionRequest);
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
        ;
  }

  private static BooleanBuilder getConditionBuilder(QuestionRequest QuestionRequest) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (Objects.nonNull(QuestionRequest)) {
      if (Objects.nonNull(QuestionRequest.getFromDt()) && Objects.nonNull(
          QuestionRequest.getToDt())) {
        booleanBuilder.and(question.createdDt.between(
            QuestionRequest.getFromDt(), QuestionRequest.getToDt())
        );
      }
      if (!StringUtils.isEmpty(QuestionRequest.getTitle())) {
        booleanBuilder.and(question.title.like("%" + QuestionRequest.getTitle() + "%"));
      }
      if (!StringUtils.isEmpty(QuestionRequest.getUrl())) {
        booleanBuilder.and(question.url.like("%" + QuestionRequest.getUrl() + "%"));
      }
      if (!StringUtils.isEmpty(QuestionRequest.getFromSource())) {
        booleanBuilder.and(question.fromSource.like("%" + QuestionRequest.getFromSource() + "%"));
      }
      if (!StringUtils.isEmpty(QuestionRequest.getQuestionType())) {
        booleanBuilder.and(
            question.questionType.like("%" + QuestionRequest.getQuestionType() + "%"));
      }
      if (QuestionRequest.getReviewCount() > 0) {
        booleanBuilder.and(question.reviewCount.eq(QuestionRequest.getReviewCount()));
      }
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
