package com.algo.question.application;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionCustomRepository;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import java.util.NoSuchElementException;
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
  public Page<QuestionResponse> findPaginatedForQuestions(QuestionRequest QuestionRequest,
      Pageable pageable) {
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
    UserInfo userInfo = userInfoRepository.findUserInfoByEmail(email);
    Question addQuestion = addQuestionRequest.converTnEntity(modelMapper);
    addQuestion.setUserInfo(userInfo);
    return questionRepository
        .save(addQuestion)
        .converToDto(modelMapper)
        ;
  }

  @Transactional
  public QuestionResponse updateQuestion(long questionId, QuestionRequest QuestionRequest) {
    Question question = QuestionRequest.converTnEntity(modelMapper);
    Question targetQuestion = null;
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
