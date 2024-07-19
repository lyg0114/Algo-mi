package com.algo.profile.application;

import java.io.IOException;
import java.io.InputStream;
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

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.infrastructure.AuthenticationUtil;
import com.algo.storage.FileSystemStorageService;
import com.algo.storage.StorageProperties;
import com.algo.storage.domain.FileRepository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.profile.application
 * @since : 30.04.24
 */
@ActiveProfiles("test")
@DisplayName("썸네일 조회, 등록, 수정, 삭제 테스트")
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ProfileServiceTest {

	@Autowired AuthenticationUtil authenticationUtil;
	private StorageProperties properties = new StorageProperties();
	private ProfileService profileService;
	@Autowired private FileRepository fileRepository;
	@Autowired private UserInfoRepository userInfoRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		properties.setLocation("build/static/images/" + Math.abs(new Random().nextLong()));
		profileService = new ProfileService(
			new FileSystemStorageService(properties, fileRepository, userInfoRepository, authenticationUtil),
			userInfoRepository, authenticationUtil
		);
	}

	// 아래의 경로의 이미지 파일을 읽어와서 MockMultipartFile 파일 생성
	// src/test/resources/images/sample.jpg
	private MockMultipartFile createMockImageFile() {
		String fileName = "sample.jpg";
		InputStream inputStream = getClass().getResourceAsStream("/images/" + fileName);
		byte[] bytes;
		try {
			bytes = inputStream.readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// MockMultipartFile 생성
		return new MockMultipartFile(
			"file",
			fileName,
			MediaType.IMAGE_JPEG_VALUE,
			bytes
		);
	}

	@Test
	void storeProfileTest() {
		String email = authenticationUtil.getEmail();
		userInfoRepository.save(
			UserInfo.builder().userId(1L).userName("user-1").email(email).passwd("passowrd-1").role("ROLE_USER").isActivate(true).build());
		MockMultipartFile mockFile = createMockImageFile();
		profileService.storeProfile(mockFile);
	}
}