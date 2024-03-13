package com.algo.recovery.application;

import com.algo.question.dto.QuestionResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 22.12.23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RecoveryService {

  private final WebApplicationContext context;
  public static final String CREATE_RECOVERY_TARGETS_BY_YESTER_DAY = "CreateRecoveryTargetsByYesterDay";

  public List<QuestionResponse> createRecoveryTargets() {
    CreateRecovery createRecovery = null;
    assert context != null;
    createRecovery = context.getBean(CREATE_RECOVERY_TARGETS_BY_YESTER_DAY, CreateRecovery.class);
    return createRecovery.createRecoveryTargets();
  }

  public String createTitle() {
    LocalDate now = LocalDate.now();
    return "[" + now + " : 복습 리스트 ]";
  }

  public String createText() {
    List<QuestionResponse> targets = createRecoveryTargets();
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
