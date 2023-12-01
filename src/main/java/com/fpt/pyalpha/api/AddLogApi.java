package com.fpt.pyalpha.api;

import com.fpt.pyalpha.dto.LogDto;
import com.fpt.pyalpha.service.IAddLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddLogApi {

  private final IAddLogService addLogService;

  @PostMapping("/logs/add")
  public ResponseEntity<?> addLog(@RequestBody LogDto logDto) {
    try {
      addLogService.addLog(logDto);
      return ResponseEntity.ok().body("Log added successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Log added failed.Error: " + e.getMessage());
    }
  }
}
