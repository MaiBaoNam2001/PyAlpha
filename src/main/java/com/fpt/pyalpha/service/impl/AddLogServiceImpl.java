package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.LogDto;
import com.fpt.pyalpha.entity.Log;
import com.fpt.pyalpha.entity.Script;
import com.fpt.pyalpha.entity.User;
import com.fpt.pyalpha.repository.ILogRepository;
import com.fpt.pyalpha.repository.IScriptRepository;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IAddLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddLogServiceImpl implements IAddLogService {

  private final IUserRepository userRepository;
  private final IScriptRepository scriptRepository;
  private final ILogRepository logRepository;

  @Override
  public void addLog(LogDto logDto) {
    if (userRepository.findByUsername(logDto.getUsername()).isEmpty()) {
      throw new RuntimeException("User not found!");
    }
    if (scriptRepository.findById(logDto.getScript()).isEmpty()) {
      throw new RuntimeException("Script not found!");
    }

    User user = userRepository.findByUsername(logDto.getUsername()).get();
    Script script = scriptRepository.findById(logDto.getScript()).get();

    Log log = new Log();
    log.setUser(user);
    log.setExecuteTime(logDto.getExecuteTime());
    log.setCpuTime(logDto.getCpuTime());
    log.setStatus(logDto.getStatus());
    log.setInput(logDto.getInput());
    log.setOutput(logDto.getOutput());
    log.setError(logDto.getError());
    log.setScript(script);
    logRepository.save(log);
  }
}
