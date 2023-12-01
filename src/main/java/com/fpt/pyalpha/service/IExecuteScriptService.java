package com.fpt.pyalpha.service;

import com.fpt.pyalpha.dto.LogDto;

public interface IExecuteScriptService {

  LogDto execute(LogDto logDto);
}