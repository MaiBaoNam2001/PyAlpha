package com.fpt.pyalpha.dto;

import java.io.Serializable;
import java.time.Instant;
import lombok.Data;

@Data
public class LogDto implements Serializable {

  private Long id;
  private String username;
  private Instant executeTime;
  private Integer cpuTime;
  private Integer status;
  private String input;
  private String output;
  private String error;
  private String file;
  private Long script;
}
