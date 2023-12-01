package com.fpt.pyalpha.api;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.service.IDeleteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DeleteUserApi {

  private final IDeleteUserService deleteUserService;

  @PostMapping("/users/delete")
  public ResponseEntity<?> deleteUser(@RequestBody UserDto userDto) {
    try {
      deleteUserService.deleteUser(userDto);
      return ResponseEntity.ok("Delete user successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Delete user failed. Error: " + e.getMessage());
    }
  }
}
