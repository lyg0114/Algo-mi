package com.algo.storage;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.infrastructure.AuthenticationUtil;
import com.algo.storage.domain.FileDetail;
import com.algo.storage.domain.FileRepository;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.storage
 * @since : 26.04.24
 */
@ActiveProfiles("test")
@DisplayName("프로필이미지 등록, 수정 테스트")
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class FileSystemStorageServiceTest {

  private StorageProperties properties = new StorageProperties();

  @Autowired private FileRepository fileRepository;
  @Autowired private UserInfoRepository userInfoRepository;
  @Autowired private FileSystemStorageService fileSystemStorageService;
  @Autowired private AuthenticationUtil authenticationUtilMcok;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    properties.setLocation("build/static/images" + Math.abs(new Random().nextLong()));
    fileSystemStorageService = new FileSystemStorageService(
        properties, fileRepository, userInfoRepository, authenticationUtilMcok
    );
    fileSystemStorageService.init();
  }

  @DisplayName("파일 저장 테스트")
  @Test
  public void saveAndLoad() {
    String email = authenticationUtilMcok.getEmail();
    userInfoRepository.save(UserInfo.builder().userId(1L).userName("user-1").email(email).passwd("passowrd-1").role("ROLE_USER").isActivate(true).build());
    FileDetail savedProfile = fileSystemStorageService.store(
        new MockMultipartFile("foo",
            "foo.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, Algo".getBytes())
    );

    UserInfo upLoader = userInfoRepository.findById(1L).get();
    UserInfo fileUploader = savedProfile.getFileUploader();
    assertThat(fileUploader.getUserId()).isEqualTo(upLoader.getUserId());
    assertThat(fileSystemStorageService.load(savedProfile.getFileId())).exists();
    assertThat(fileSystemStorageService.loadAsResource(savedProfile.getFileId())).isNotNull();
  }
}
