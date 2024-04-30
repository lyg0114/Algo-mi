package com.algo.storage;

import com.algo.storage.domain.FileDetail;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  void init();

  FileDetail store(MultipartFile file);

  Stream<Path> loadAll();

  Path load(Long fileId);

  Resource loadAsResource(Long fileId);

  void deleteAll();

}
