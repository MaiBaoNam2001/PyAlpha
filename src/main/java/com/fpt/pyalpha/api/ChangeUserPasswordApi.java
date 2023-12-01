package com.fpt.pyalpha.api;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.service.IChangeUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChangeUserPasswordApi {

  private final IChangeUserPasswordService changeUserPasswordService;

  @PostMapping("/users/change-password")
  public ResponseEntity<?> changeUserPassword(@RequestBody UserDto userDto) {
    try {
      changeUserPasswordService.changeUserPassword(userDto);
      return ResponseEntity.ok("Change password successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Change password failed. Error: " + e.getMessage());
    }
  }
}
