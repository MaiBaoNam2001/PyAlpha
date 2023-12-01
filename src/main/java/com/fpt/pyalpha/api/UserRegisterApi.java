package com.fpt.pyalpha.api;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.service.IUserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRegisterApi {

  private final IUserRegisterService userRegisterService;

  @PostMapping(value = "/users/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> register(@RequestBody UserDto userDto) {
    try {
      userRegisterService.registerUser(userDto);
      return ResponseEntity.ok("Register successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Register failed. Error: " + e.getMessage());
    }
  }
}
