package com.fpt.pyalpha.service;

import com.fpt.pyalpha.dto.LogDto;
import java.util.List;

public interface IFetchLogsByScriptIdService {

  List<LogDto> fetchLogsByScriptId(Long scriptId);
}
