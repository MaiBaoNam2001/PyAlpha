package com.fpt.pyalpha.service;

import com.fpt.pyalpha.dto.ScriptDto;
import org.springframework.web.multipart.MultipartFile;

public interface IAddScriptService {

  void addScript(ScriptDto scriptDto, MultipartFile file);
}
