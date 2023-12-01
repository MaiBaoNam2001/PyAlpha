package com.fpt.pyalpha.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileInfo implements Serializable {

  private String fileName;
  private String url;
}
