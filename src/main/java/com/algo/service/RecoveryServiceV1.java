package com.algo.service;

import com.algo.model.entity.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 22.12.23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RecoveryServiceV1 implements RecoveryService {

  @Override
  public List<Question> getRecoveryTargets() {
    return null;
  }
}
