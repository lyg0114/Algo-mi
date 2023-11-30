package com.algo.repository;

import com.algo.model.entity.UserInfo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.repository
 * @since : 21.11.23
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

  @Query("SELECT user FROM UserInfo user ORDER BY user.userName")
  @Transactional(readOnly = true)
  List<UserInfo> findUserInfos();

  UserInfo findUserInfoByEmail(String email);
}
