package com.fpt.pyalpha.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserDto implements Serializable {

  private Long id;
  private String username;
  private String password;
  private String role;
}
