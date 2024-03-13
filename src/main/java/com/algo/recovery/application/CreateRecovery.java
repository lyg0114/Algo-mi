package com.algo.recovery.application;

import com.algo.question.dto.QuestionResponse;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.algo.recovery.application
 * @since : 13.03.24
 */
public interface CreateRecovery {

  List<QuestionResponse> createRecoveryTargets();
}
