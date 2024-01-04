package com.algo.recovery.application;

import com.algo.question.dto.QuestionDto;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 22.12.23
 */
public interface RecoveryService {

  void getRecoveryTargetsAndNoteToUser();

  List<QuestionDto> getRecoveryTargets();
}
