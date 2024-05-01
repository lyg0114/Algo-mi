package com.algo.profile.ui;

import com.algo.profile.application.ProfileService;
import com.algo.profile.dto.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : iyeong-gyo
 * @package : com.algo.user.ui
 * @since : 26.04.24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileRestController {

  private final ProfileService profileService;

  @GetMapping("/info")
  public ResponseEntity<ProfileResponse> getProfileInfo() {
    return new ResponseEntity<>(
        profileService.getProfileInfo(),
        HttpStatus.OK
    );
  }

  //TODO : 테스트코드 작성, 예외처리
  @GetMapping("/thumnail")
  public ResponseEntity<Resource> getProfileImage() {
    Resource resource = profileService.getImage();
    if (resource.exists() && resource.isReadable()) {
      return ResponseEntity.ok().body(resource);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  //TODO : ResponseEntity에서 공통된 부분을 추출할 수 있도록 개선 필요
  //TODO : 테스트코드 작성, 예외처리, 유효성 처리
  @PostMapping("/upload")
  public ResponseEntity<ProfileResponse> uploadThumNailFile(@RequestParam("file") MultipartFile file) {
    profileService.storeProfile(file);
    log.info("file upload success");

    return new ResponseEntity<>(
        ProfileResponse.builder().build(), HttpStatus.CREATED
    );
  }

  //TODO : 프로필 정보 수정 로직 추가 필요
  //TODO : 썸네일 수정, 삭제 로직 추가 필요
}
