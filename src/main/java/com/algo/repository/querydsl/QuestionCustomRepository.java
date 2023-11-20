package com.algo.repository.querydsl;

import static com.algo.model.entity.QQuestion.question;

import com.algo.model.dto.QuestionDto;
import com.algo.model.entity.Question;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
    return new PageImpl<>(
        getQuestionDtoList(jpaQuery, pageable),
        pageable,
        getTotalCount(jpaQuery)
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
    if (!StringUtils.isEmpty(questionDto.getTitle())) {
      booleanBuilder.and(question.title.like("%" + questionDto.getTitle() + "%"));
    }
    if (!StringUtils.isEmpty(questionDto.getFromSource())) {
      booleanBuilder.and(question.fromSource.like("%" + questionDto.getFromSource() + "%"));
    }
    return booleanBuilder;
  }

  private List<QuestionDto> getQuestionDtoList(JPAQuery<Question> jpaQuery, Pageable pageable) {
    return jpaQuery
        .offset(pageable.getOffset())
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
