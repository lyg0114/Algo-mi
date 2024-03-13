package com.algo.recovery.application;

import com.algo.alert.application.EmailService;
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
 * @package : com.algo.service
 * @since : 22.12.23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RecoveryService {

  private final QuestionCustomRepository questionCustomRepository;
  private final ModelMapper modelMapper;

  public List<QuestionResponse> getRecoveryTargets() {
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

  public String createTitle() {
    LocalDate now = LocalDate.now();
    return "[" + now + " : 복습 리스트 ]";
  }

  public String createText() {
    List<QuestionResponse> targets = getRecoveryTargets();
    StringBuffer sb = new StringBuffer();
    if (!targets.isEmpty()) {
      sb.append("######################\n");
      sb.append("## 오늘 복습할 문제 ##\n");
      sb.append("######################\n");
      for (QuestionResponse target : targets) {
        sb.append(target.getTitle())
            .append(" : ")
            .append(target.getUrl())
            .append("\n");
      }
    } else {
      sb.append("##############\n");
      sb.append("## 분발하자 ##\n");
      sb.append("##############\n");
    }
    return sb.toString();
  }
}
