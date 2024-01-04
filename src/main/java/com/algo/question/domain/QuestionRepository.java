package com.algo.question.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository
 * @since : 18.11.23
 */
public interface QuestionRepository extends CrudRepository<Question, Long> {
}
