package com.algo.recovery.application;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.QuestionCustomRepository;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.sample.QuestionSample;
import com.algo.recovery.dto.RecoveryContents;

/**
 * @author : iyeong-gyo
 * @package : com.algo.recovery.application
 * @since : 06.04.24
 */
@ActiveProfiles("test")
@DisplayName("복습 대상 문제 추출 테스트")
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class RecoveryServiceTest {

	@Autowired RecoveryService recoveryService;
	@Autowired QuestionRepository questionRepository;
	@Autowired QuestionCustomRepository questionCustomRepository;
	@Autowired UserInfoRepository userInfoRepository;

	@DisplayName("사용자별 복습 대상 문제 추출")
	@Test
	void shouldGetRecoveryTargets() {
		QuestionSample.createSampleQuestionsForRecovery(questionRepository, userInfoRepository);
		List<RecoveryContents> contents = recoveryService.createContents();
		List<String> emails = List.of("user-1@example.com", "user-2@example.com", "user-3@example.com");
		assertThat(contents.size()).isEqualTo(3);
		assertThat(emails.contains(contents.get(0).getEmail())).isTrue();
		assertThat(emails.contains(contents.get(1).getEmail())).isTrue();
		assertThat(emails.contains(contents.get(2).getEmail())).isTrue();
	}
}