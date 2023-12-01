package com.fpt.pyalpha.api;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.service.IUpdateScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UpdateScriptApi {

  private final IUpdateScriptService updateScriptService;

  @PostMapping(value = "/scripts/update")
  public ResponseEntity<?> updateScript(@RequestBody ScriptDto scriptDto) {
    try {
      updateScriptService.updateScript(scriptDto);
      return ResponseEntity.ok("Script updated successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Script updated failed. Error: " + e.getMessage());
    }
  }
}
