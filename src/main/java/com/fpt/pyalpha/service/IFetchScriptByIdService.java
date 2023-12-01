package com.fpt.pyalpha.service;

import com.fpt.pyalpha.dto.ScriptDto;

public interface IFetchScriptByIdService {

  ScriptDto fetchScriptById(Long id);
}
