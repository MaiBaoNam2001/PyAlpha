package com.fpt.pyalpha.controller;

import com.fpt.pyalpha.service.impl.UserDetailsImpl;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  @GetMapping("/")
  public String index() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken) {
      return "index";
    }
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String authority = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .findFirst().orElseThrow(() -> new RuntimeException("Role not found!"));
    return authority.equals("ROLE_USER") ? "redirect:/user/" : "redirect:/admin/";
  }
}
