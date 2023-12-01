package com.fpt.pyalpha.api;

import com.fpt.pyalpha.service.IFetchScriptByIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FetchScriptByIdApi {

  private final IFetchScriptByIdService fetchScriptByIdService;

  @GetMapping("/scripts/{id}")
  public ResponseEntity<?> fetchScriptById(@PathVariable(value = "id") Long id) {
    try {
      return ResponseEntity.ok(fetchScriptByIdService.fetchScriptById(id));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Fetch script failed. Error: " + e.getMessage());
    }
  }
}