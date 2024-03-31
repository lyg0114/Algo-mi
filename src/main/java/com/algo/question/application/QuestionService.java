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
    Question Queston = null;
    try {
      Queston = questionRepository.findById(questionId).orElseThrow();
    } catch (NoSuchElementException e) {
      return null;
    }
    return Queston;
  }

  @Transactional
  public QuestionResponse addQuestion(String email, QuestionRequest addQuestionRequest) {
    UserInfo userInfo = userInfoRepository.findUserInfoByEmailAndIsActivateTrue(email);
    if(Objects.isNull(userInfo)){
      //TODO : ExceptionControllerAdvice 에서 예외를 받아서  처리해주는 코드 작성 필요.
      throw new IllegalArgumentException("존재하지 않는 사용자 입니다.");
    }
    Question addQuestion = addQuestionRequest.converTnEntity();
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
    //TODO : update 사용자 정보 핸들링하는 로직 필요
    try {
      targetQuestion = questionRepository.findById(questionId).orElseThrow();
      targetQuestion.update(question);
      return targetQuestion.converToDto(modelMapper);
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  @Transactional
  public void deleteQuestion(long questionId) {
    questionRepository.deleteById(questionId);
  }
}
