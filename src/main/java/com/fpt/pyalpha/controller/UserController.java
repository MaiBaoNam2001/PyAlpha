package com.fpt.pyalpha.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

  @GetMapping("/")
  public String index() {
    return "user_page";
  }

  @GetMapping("/execute/{id}")
  public String execute(@PathVariable("id") Long id) {
    return "execute";
  }
}
