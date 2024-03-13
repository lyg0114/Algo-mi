package com.algo.recovery.application;

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
@Service("CreateRecoveryTargetsByYesterDay")
public class CreateRecoveryTargets implements CreateRecovery {

  private final QuestionCustomRepository questionCustomRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<QuestionResponse> createRecoveryTargets() {
    return questionCustomRepository
        .findQuestions(
            QuestionRequest.builder()
                .fromDt(LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.MIDNIGHT))
                .toDt(LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.MAX))
                .build()
        )
        .fetch()
        .stream()
        .map(question -> question.converToDto(modelMapper))
        .collect(Collectors.toList());
  }
}
