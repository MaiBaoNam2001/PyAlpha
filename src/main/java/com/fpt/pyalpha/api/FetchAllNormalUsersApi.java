package com.fpt.pyalpha.api;

import com.fpt.pyalpha.service.IFetchAllNormalUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FetchAllNormalUsersApi {

  private final IFetchAllNormalUsersService fetchAllNormalUsersService;

  @GetMapping("/users/fetch-all")
  public ResponseEntity<?> fetchAllNormalUsers() {
    try {
      return ResponseEntity.ok(fetchAllNormalUsersService.fetchAllNormalUsers());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest()
          .body("Fetch all normal users failed. Error: " + e.getMessage());
    }
  }
}
