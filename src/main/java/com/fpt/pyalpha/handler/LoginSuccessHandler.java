package com.fpt.pyalpha.handler;

import com.fpt.pyalpha.service.impl.UserDetailsImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String role = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).findFirst()
        .orElseThrow(() -> new RuntimeException("Role not found!"));
    String redirectURL = role.equals("ROLE_USER") ? "/user/" : "/admin/";
    response.sendRedirect(redirectURL);
  }
}
