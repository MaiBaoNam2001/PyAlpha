package com.fpt.pyalpha.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.service.IAddScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddScriptApi {

  private final IAddScriptService addScriptService;
  private final ObjectMapper objectMapper;

  @PostMapping(value = "/scripts/add", consumes = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<?> addScript(@RequestPart("scriptJsonString") String scriptJsonString,
      @RequestPart("file") MultipartFile file) {
    try {
      ScriptDto scriptDto = objectMapper.readValue(scriptJsonString, ScriptDto.class);
      addScriptService.addScript(scriptDto, file);
      return ResponseEntity.ok("Script added successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Script added failed. Error: " + e.getMessage());
    }
  }
}
