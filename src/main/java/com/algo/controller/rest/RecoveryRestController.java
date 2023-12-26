package com.algo.controller.rest;

import com.algo.model.dto.QuestionDto;
import com.algo.service.RecoveryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
@RequestMapping("/recovery")
public class RecoveryRestController {

  private final RecoveryService recoveryService;

  @GetMapping
  public ResponseEntity<List<QuestionDto>> getRecoverys() {
    List<QuestionDto> recoveryTargets = recoveryService.getRecoveryTargets();
    return new ResponseEntity<>(recoveryTargets, HttpStatus.OK);
  }

  @GetMapping("/send-user")
  @Scheduled(cron = "0 0 13 * * ?")
  public void sendRecoverys() {
    recoveryService.getRecoveryTargetsAndNoteToUser();
  }
}
