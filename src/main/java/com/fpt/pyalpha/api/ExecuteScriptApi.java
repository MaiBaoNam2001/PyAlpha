package com.fpt.pyalpha.api;

import com.fpt.pyalpha.dto.LogDto;
import com.fpt.pyalpha.service.IExecuteScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExecuteScriptApi {

  private final IExecuteScriptService executeScriptService;

  @PostMapping("/execute-script")
  public ResponseEntity<?> executeScript(@RequestBody LogDto logDto) {
    try {
      return ResponseEntity.ok(executeScriptService.execute(logDto));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
