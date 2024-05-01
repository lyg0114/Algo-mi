package com.algo.profile.application;

import com.algo.profile.dto.ProfileRequest;
import com.algo.profile.dto.ProfileResponse;
import java.util.Objects;
import org.springframework.http.MediaType;
import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.infrastructure.AuthenticationUtil;
import com.algo.storage.StorageService;
import com.algo.storage.domain.FileDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
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
  private final AuthenticationUtil authenticationUtil;

  //TODO : 썸네일 파일 저장시 파일 사이즈 작게 처리
  @Transactional
  public void storeProfile(MultipartFile file) {
    if (file == null) { throw new IllegalArgumentException("Failed to store empty file."); }
    if (!isImageFile(file)) { throw new IllegalArgumentException("this is not image file"); }

    // 썸네일 파일 저장
    FileDetail fileDetail = storageService.store(file);

    // 사용자의 썸네일 정보 업데이트
    UserInfo userInfo = fileDetail.getFileUploader();
    userInfo.updateProfileImage(fileDetail);
    userInfoRepository.save(userInfo);
  }

  private boolean isImageFile(MultipartFile file) {
    MediaType fileType = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
    return fileType.getType().equals("image");  }

  public Resource getImage() {
    UserInfo userInfo = userInfoRepository
        .findUserInfoByEmailAndIsActivateTrue(authenticationUtil.getEmail())
        .orElseThrow();
    Long fileId = userInfo.getProfile().getFileId();
    return storageService.loadAsResource(fileId);
  }

  public ProfileResponse getProfileInfo() {
    return userInfoRepository
        .findUserInfoByEmailAndIsActivateTrue(authenticationUtil.getEmail())
        .orElseThrow()
        .converToProfileResponse();
  }

  @Transactional
  public ProfileResponse updateProfileInfo(ProfileRequest profileRequest) {
    UserInfo userInfo = userInfoRepository
        .findUserInfoByEmailAndIsActivateTrue(authenticationUtil.getEmail())
        .orElseThrow();
    userInfo.updateProfileInfo(profileRequest);
    return userInfo.converToProfileResponse();
  }
}
