package com.fpt.pyalpha.service;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

  void initDirectory();

  void store(MultipartFile file);

  Resource load(String filename);

  void delete(String filename);

  Stream<Path> loadAll();

  void deleteAll();
}
