package com.fpt.pyalpha.api;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.service.IDeleteScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DeleteScriptApi {

  private final IDeleteScriptService deleteScriptService;

  @PostMapping("/scripts/delete")
  public ResponseEntity<?> deleteScript(@RequestBody ScriptDto scriptDto) {
    try {
      deleteScriptService.deleteScript(scriptDto);
      return ResponseEntity.ok("Script deleted successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Script deleted failed. Error: " + e.getMessage());
    }
  }
}
