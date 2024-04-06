package com.algo.recovery.application;

import static com.algo.recovery.application.RecoveryService.CREATE_RECOVERY_TARGETS_BY_YESTER_DAY;

import com.algo.question.domain.QuestionCustomRepository;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.algo.recovery.application
 * @since : 13.03.24
 */
@Slf4j
@RequiredArgsConstructor
@Service(CREATE_RECOVERY_TARGETS_BY_YESTER_DAY)
public class CreateRecoveryTargets implements CreateRecovery {

  public static final String SCHEDULE = "SCHEDUE";
  private final QuestionCustomRepository questionCustomRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<QuestionResponse> createRecoveryTargets() {
    QuestionRequest questionRequest = QuestionRequest.builder()
        .fromDt(LocalDateTime.of(LocalDate.now().minusDays(3L), LocalTime.MIDNIGHT))
        .toDt(LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.MAX))
        .build();
    questionRequest.setEmail(SCHEDULE);
    return questionCustomRepository
        .findQuestions(questionRequest)
        .fetch()
        .stream()
        .map(question -> question.converToDto(modelMapper))
        .collect(Collectors.toList());
  }
}
