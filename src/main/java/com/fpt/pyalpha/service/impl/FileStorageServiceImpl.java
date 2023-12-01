package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.service.IFileStorageService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements IFileStorageService {

  private static final Path ROOT = Paths.get("uploads");

  @Override
  public void initDirectory() {
    try {
      Files.createDirectories(ROOT);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void store(MultipartFile file) {
    try {
      Files.copy(file.getInputStream(),
          ROOT.resolve(Objects.requireNonNull(file.getOriginalFilename())));
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("File already exists!");
      }
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path path = ROOT.resolve(filename);
      Resource resource = new UrlResource(path.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void delete(String filename) {
    try {
      Path path = ROOT.resolve(filename);
      Files.deleteIfExists(path);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(ROOT, 1).filter(path -> !path.equals(ROOT)).map(ROOT::relativize);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Could not load the files!");
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(ROOT.toFile());
  }
}
