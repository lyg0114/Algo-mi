package com.algo.repository;

import com.algo.model.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository
 * @since : 21.11.23
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
}
