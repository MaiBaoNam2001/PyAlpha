package com.fpt.pyalpha.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  @GetMapping("/")
  public String index() {
    return "admin_page";
  }

  @GetMapping("/manage-user")
  public String manageUser() {
    return "admin_manage_user";
  }

  @GetMapping("/scripts/{id}")
  public String scriptDetails(@PathVariable("id") Long id) {
    return "admin_script_details";
  }
}
