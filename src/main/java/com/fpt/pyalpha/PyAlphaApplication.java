package com.fpt.pyalpha;

import com.fpt.pyalpha.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class PyAlphaApplication implements CommandLineRunner {

  private final IFileStorageService IFileStorageService;

  public static void main(String[] args) {
    SpringApplication.run(PyAlphaApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    IFileStorageService.initDirectory();
  }
}
