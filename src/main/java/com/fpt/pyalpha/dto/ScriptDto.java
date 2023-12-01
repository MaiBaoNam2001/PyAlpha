package com.fpt.pyalpha.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ScriptDto implements Serializable {

  private Long id;
  private String title;
  private String description;
  private String user;
  private String file;
}
