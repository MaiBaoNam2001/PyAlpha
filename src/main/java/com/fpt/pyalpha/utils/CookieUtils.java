package com.fpt.pyalpha.utils;

import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

  public static String getValue(HttpServletRequest request, String key) {
    Cookie[] cookies =
        Objects.nonNull(request.getCookies()) ? request.getCookies() : new Cookie[]{};
    return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(key))
        .map(Cookie::getValue).findFirst().orElse(null);
  }
}
