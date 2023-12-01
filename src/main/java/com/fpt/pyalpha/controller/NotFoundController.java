package com.fpt.pyalpha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundController {

  @GetMapping("/not-found")
  public String notFound() {
    return "404";
  }
}
