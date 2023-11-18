package com.algo.repository;

import com.algo.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository
 * @since : 18.11.23
 */
public interface QuestionRepository extends CrudRepository<Question, Long> {

  @Query("SELECT question FROM Question question")
  @Transactional(readOnly = true)
  Page<Question> findAll(Pageable pageable);

}
