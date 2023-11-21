package com.algo.repository;

import com.algo.model.entity.Question;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository
 * @since : 18.11.23
 */
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
