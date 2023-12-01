package com.fpt.pyalpha.api;

import com.fpt.pyalpha.service.IFetchAllScriptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FetchAllScriptsApi {

  private final IFetchAllScriptsService fetchAllScriptsService;

  @GetMapping("/scripts/fetch-all")
  public ResponseEntity<?> fetchAllScripts() {
    try {
      return ResponseEntity.ok(fetchAllScriptsService.fetchAllScripts());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Fetch all scripts failed. Error: " + e.getMessage());
    }
  }
}
