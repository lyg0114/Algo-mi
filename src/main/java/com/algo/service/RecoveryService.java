package com.algo.service;

import com.algo.model.entity.Question;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 22.12.23
 */
public interface RecoveryService {

  List<Question> getRecoveryTargets();
}
