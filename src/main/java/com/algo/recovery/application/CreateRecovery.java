package com.algo.recovery.application;

import java.util.List;

import com.algo.question.dto.QuestionResponse;

/**
 * @author : iyeong-gyo
 * @package : com.algo.recovery.application
 * @since : 13.03.24
 */
public interface CreateRecovery {

	List<QuestionResponse> createRecoveryTargets();
}
