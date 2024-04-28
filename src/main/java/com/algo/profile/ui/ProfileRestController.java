package com.algo.profile.ui;

import com.algo.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final StorageService storageService;

  //TODO : 반환값 String 에서 ResponseEntity로 개선
  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file") MultipartFile file) {
    storageService.store(file);
    log.info("file upload success");
    return "SUCCESS";
  }
}
