package com.algo.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.algo.storage.domain.FileDetail;

public interface StorageService {

	void init();

	FileDetail store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(Long fileId);

	Resource loadAsResource(Long fileId);

	void deleteAll();

}
