package com.algo.storage;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.auth.infrastructure.AuthenticationUtil;
import com.algo.storage.domain.FileDetail;
import com.algo.storage.domain.FileRepository;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

  private final Path rootLocation;
  private final FileRepository fileRepository;
  private final UserInfoRepository userInfoRepository;

  @Autowired
  public FileSystemStorageService(
      StorageProperties properties, FileRepository fileRepository, UserInfoRepository userInfoRepository) {
    if (properties.getLocation().trim().isEmpty()) {
      throw new StorageException("File upload location can not be Empty.");
    }
    this.rootLocation = Paths.get(properties.getLocation());
    this.fileRepository = fileRepository;
    this.userInfoRepository = userInfoRepository;
  }

  @Transactional
  @Override
  public FileDetail store(MultipartFile file) {
    FileDetail savedProfile;
    try {
      // file 존재유무 검증
      if (file.isEmpty()) { throw new StorageException("Failed to store empty file."); }

      // 파일정보 DB에 저장
      UserInfo userInfo = userInfoRepository
          .findUserInfoByEmailAndIsActivateTrue(AuthenticationUtil.getEmail()).orElseThrow();
      //TODO : FileDetail 클래스에 필요한 정보를 맾핑하고 DB에 저장하도록 수정 필요
      FileDetail fileDetail = FileDetail
          .builder()
          .fileSize(file.getSize())
          .fileUploader(userInfo)
          .build();
      savedProfile = fileRepository.save(fileDetail);

      //디렉토리 생성
      String directoryPath = userInfo.getUserId() + "/" + savedProfile.getFileId();
      Path directory = Paths.get(rootLocation.toString(), directoryPath);
      Files.createDirectories(directory);

      // 파일 저장 경로 계산
      Path fileUri = Paths.get(directoryPath, file.getOriginalFilename());
      Path destinationFile = this.rootLocation
          .resolve(fileUri)
          .normalize()
          .toAbsolutePath();
      savedProfile.updateFileUri(fileUri);

      // rootLocation 검증
      if (!destinationFile.getParent().getParent().getParent().equals(this.rootLocation.toAbsolutePath())) {
        throw new StorageException("Cannot store file outside current directory.");
      }

      // 파일 저장
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }

    return savedProfile;
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.rootLocation, 1)
          .filter(path -> !path.equals(this.rootLocation))
          .map(this.rootLocation::relativize);
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }

  }

  @Override
  public Path load(Long fileId) {
    FileDetail fileDetail = fileRepository.findById(fileId).orElseThrow();
    return rootLocation.resolve(fileDetail.getFileUri());
  }

  @Override
  public Resource loadAsResource(Long fileId) {
    try {
      Path file = load(fileId);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException("Could not read fileId: " + fileId);
      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read fileId: " + fileId, e);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }
}
