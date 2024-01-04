package com.algo.question.domain;


import static com.algo.question.domain.QQuestion.question;

import com.algo.question.dto.QuestionDto;
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

  public Page<QuestionDto> findPaginatedForQuestions(QuestionDto questionDto, Pageable pageable) {
    JPAQuery<Question> jpaQuery = findQuestions(questionDto);
    long totalCount = getTotalCount(jpaQuery);
    return new PageImpl<>(
        getQuestionDtoList(jpaQuery, pageable),
        pageable,
        totalCount
    );
  }

  public JPAQuery<Question> findQuestions(QuestionDto questionDto) {
    return queryFactory
        .select(question)
        .from(question)
        .where(getConditionBuilder(questionDto))
        ;
  }

  private static BooleanBuilder getConditionBuilder(QuestionDto questionDto) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (Objects.nonNull(questionDto)) {
      if (Objects.nonNull(questionDto.getFromDt()) && Objects.nonNull(questionDto.getToDt())) {
        booleanBuilder.and(question.createdDt.between(
            questionDto.getFromDt(), questionDto.getToDt())
        );
      }
      if (!StringUtils.isEmpty(questionDto.getTitle())) {
        booleanBuilder.and(question.title.like("%" + questionDto.getTitle() + "%"));
      }
      if (!StringUtils.isEmpty(questionDto.getUrl())) {
        booleanBuilder.and(question.url.like("%" + questionDto.getUrl() + "%"));
      }
      if (!StringUtils.isEmpty(questionDto.getFromSource())) {
        booleanBuilder.and(question.fromSource.like("%" + questionDto.getFromSource() + "%"));
      }
      if (!StringUtils.isEmpty(questionDto.getQuestionType())) {
        booleanBuilder.and(question.questionType.like("%" + questionDto.getQuestionType() + "%"));
      }
      if (questionDto.getReviewCount() > 0) {
        booleanBuilder.and(question.reviewCount.eq(questionDto.getReviewCount()));
      }
    }
    return booleanBuilder;
  }

  private List<QuestionDto> getQuestionDtoList(JPAQuery<Question> jpaQuery, Pageable pageable) {
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
