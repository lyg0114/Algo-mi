//package com.algo.mock.sample;
//
//import com.algo.model.entity.Question;
//import com.algo.model.entity.UserInfo;
//import com.algo.repository.QuestionRepository;
//import com.algo.repository.UserInfoRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//
///**
// * @author : iyeong-gyo
// * @package : com.algo.mock
// * @since : 04.12.23
// */
//@ActiveProfiles("dev")
//@SpringBootTest
//public class CreateSampleData {
//
//  @Autowired
//  private QuestionRepository questionRepository;
//  @Autowired
//  private UserInfoRepository userInfoRepository;
//  private PasswordEncoder passwordEncoder;
//
//  @BeforeEach
//  public void beforeEach() {
//    passwordEncoder = new BCryptPasswordEncoder();
//  }
//
//  @Test
//  void makeSampleData() {
//    userInfoRepository.deleteAll();
//    UserInfo saveUserInfo = userInfoRepository.save(
//        UserInfo.builder().userName("kyle").email("user@example.com")
//            .passwd(passwordEncoder.encode("password")).role("ROLE_USER").build()
//    );
//
//    for (int i = 0; i < 150; i++) {
//      questionRepository.save(
//          Question.builder()
//              .title("title-" + i)
//              .url("http://sample-url/" + i)
//              .reviewCount(3)
//              .fromSource("leetcode-" + i)
//              .userInfo(saveUserInfo)
//              .build()
//      );
//    }
//  }
//}
