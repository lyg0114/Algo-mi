package com.algo.question.application;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionCustomRepository;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 18.11.23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionCustomRepository questionCustomRepository;
  private final QuestionRepository questionRepository;
  private final UserInfoRepository userInfoRepository;
  private final ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public Page<QuestionResponse> findPaginatedForQuestions(
      QuestionRequest QuestionRequest, Pageable pageable
  ) {
    return questionCustomRepository.findPaginatedForQuestions(QuestionRequest, pageable);
  }

  @Transactional(readOnly = true)
  public Question findQuestionById(long questionId) {
    return questionRepository.findById(questionId)
        .orElseThrow(() -> new NoSuchElementException("문제 정보를 찾을 수 없습니다."));
  }

  @Transactional
  public QuestionResponse addQuestion(QuestionRequest question) {
    UserInfo userInfo = userInfoRepository.findUserInfoByEmailAndIsActivateTrue(
        question.getEmail());
    if (Objects.isNull(userInfo)) {
      throw new NoSuchElementException("존재하지 않는 사용자 입니다.");
    }
    Question addQuestion = question.converTnEntity();
    addQuestion.setUserInfo(userInfo);
    return questionRepository
        .save(addQuestion)
        .converToDto(modelMapper)
        ;
  }

  @Transactional
  public QuestionResponse updateQuestion(long questionId, QuestionRequest QuestionRequest) {
    Question question = QuestionRequest.converTnEntity();
    Question targetQuestion = null;
    targetQuestion = questionRepository.findById(questionId)
        .orElseThrow(() -> new NoSuchElementException("문제 정보를 찾을 수 없습니다."));
    if (Objects.isNull(targetQuestion.getUserInfo())) {
      throw new NoSuchElementException("존재하지 않는 사용자 입니다.");
    }
    if (!targetQuestion.getUserInfo().getEmail().equals(QuestionRequest.getEmail())) {
      throw new NoSuchElementException("존재하지 않는 사용자 입니다.");
    }

    targetQuestion.update(question);
    return targetQuestion.converToDto(modelMapper);
  }

  @Transactional
  public void deleteQuestion(long questionId) {
    //TODO : delete 사용자 정보 핸들링하는 로직 필요
    questionRepository.deleteById(questionId);
  }
}
