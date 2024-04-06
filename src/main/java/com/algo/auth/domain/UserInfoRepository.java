package com.algo.auth.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository
 * @since : 21.11.23
 */
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
  UserInfo findUserInfoByEmailAndIsActivateTrue(String email);
}
