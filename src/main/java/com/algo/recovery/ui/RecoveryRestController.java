package com.algo.recovery.ui;

import com.algo.question.dto.QuestionResponse;
import com.algo.recovery.application.RecoveryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : iyeong-gyo
 * @package : com.algo.controller.rest
 * @since : 19.12.23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recovery")
public class RecoveryRestController {

  private final RecoveryService recoveryService;

  @GetMapping
  public ResponseEntity<List<QuestionResponse>> getRecoverys() {
    List<QuestionResponse> recoveryTargets = recoveryService.getRecoveryTargets();
    return new ResponseEntity<>(recoveryTargets, HttpStatus.OK);
  }
}
