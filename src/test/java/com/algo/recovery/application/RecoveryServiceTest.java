package com.algo.recovery.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * @author : iyeong-gyo
 * @package : com.algo.recovery.application
 * @since : 22.02.24
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class RecoveryServiceTest {

  @Autowired
  RecoveryService recoveryService;

  @Test
  void recoverServiceTest() {
    recoveryService.getRecoveryTargetsAndNoteToUser();
  }
}