package com.algo.profile.application;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.storage.StorageService;
import com.algo.storage.domain.FileDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : iyeong-gyo
 * @package : com.algo.profile.application
 * @since : 26.04.24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {

  private final StorageService storageService;
  private final UserInfoRepository userInfoRepository;

  //TODO : 테스트코드 작성
  @Transactional
  public void storeProfile(MultipartFile file) {
    // 썸네일 파일 저장
    FileDetail fileDetail = storageService.store(file);

    // 사용자의 썸네일 정보 업데이트
    UserInfo userInfo = fileDetail.getFileUploader();
    userInfo.updateProfile(fileDetail);
    userInfoRepository.save(userInfo);
  }
}
