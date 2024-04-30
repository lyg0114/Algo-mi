package com.algo.storage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.infrastructure.AuthenticationUtilMcok;
import com.algo.storage.domain.FileDetail;
import com.algo.storage.domain.FileRepository;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  @Autowired private FileSystemStorageService service;
  @Mock
  private Authentication authentication;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    properties.setLocation(StorageProperties.LOCATION + Math.abs(new Random().nextLong()));
    service = new FileSystemStorageService(properties, fileRepository, userInfoRepository, new AuthenticationUtilMcok());
    service.init();
  }

  @DisplayName("파일 저장 테스트")
  @Test
  public void saveAndLoad() {
    String email = "test@example.com";
    userInfoRepository.save(UserInfo.builder().userId(1L).userName("user-1").email(email).passwd("passowrd-1").role("ROLE_USER").isActivate(true).build());
    when(authentication.getName()).thenReturn(email);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    FileDetail savedProfile = service.store(
        new MockMultipartFile("foo",
            "foo.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, Algo".getBytes())
    );

    UserInfo upLoader = userInfoRepository.findById(1L).get();
    UserInfo fileUploader = savedProfile.getFileUploader();
    assertThat(fileUploader.getUserId()).isEqualTo(upLoader.getUserId());
    assertThat(service.load(savedProfile.getFileId())).exists();
    assertThat(service.loadAsResource(savedProfile.getFileId())).isNotNull();
  }
}
