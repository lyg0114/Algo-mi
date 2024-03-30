package com.algo.auth.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.auth.domain
 * @since : 26.03.24
 */
@Repository
public interface CheckEmailRepository extends CrudRepository<CheckEmail, String> {
}
