package com.algo.profile.ui;

import com.algo.storage.StorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file") MultipartFile file) {
    storageService.store(file);
    return "SUCCESS";
  }

}
