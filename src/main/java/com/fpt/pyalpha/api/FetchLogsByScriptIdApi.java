package com.fpt.pyalpha.api;

import com.fpt.pyalpha.service.IFetchLogsByScriptIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FetchLogsByScriptIdApi {

  private final IFetchLogsByScriptIdService fetchLogsByScriptIdService;

  @GetMapping("/logs/fetch/{scriptId}")
  public ResponseEntity<?> fetchLogsByScriptId(@PathVariable(value = "scriptId") Long scriptId) {
    try {
      return ResponseEntity.ok(fetchLogsByScriptIdService.fetchLogsByScriptId(scriptId));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest()
          .body("Fetch logs by script id failed. Error: " + e.getMessage());
    }
  }
}
