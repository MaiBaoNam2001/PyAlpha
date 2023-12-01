package com.fpt.pyalpha.api;

import com.fpt.pyalpha.entity.FileInfo;
import com.fpt.pyalpha.service.IFileStorageService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileStorageApi {

  private final IFileStorageService fileStorageService;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      fileStorageService.store(file);
      return ResponseEntity.ok("Upload file successfully");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Upload file failed");
    }
  }

  @GetMapping("/files/{fileName}")
  public ResponseEntity<?> getFile(@PathVariable("fileName") String fileName) {
    try {
      Resource resource = fileStorageService.load(fileName);
      return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Get file failed");
    }
  }

  @GetMapping("/files")
  public ResponseEntity<?> getAllFiles() {

    try {
      List<FileInfo> fileInfoList = fileStorageService.loadAll().map(path -> {
        String fileName = path.getFileName().toString();
        String url = MvcUriComponentsBuilder.fromMethodName(FileStorageApi.class, "getFile",
            path.getFileName().toString()).build().toString();
        return new FileInfo(fileName, url);
      }).collect(Collectors.toList());
      return ResponseEntity.ok().body(fileInfoList);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Get all files failed");
    }
  }
}
