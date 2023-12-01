package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.entity.Script;
import com.fpt.pyalpha.repository.ILogRepository;
import com.fpt.pyalpha.repository.IScriptRepository;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IDeleteScriptService;
import com.fpt.pyalpha.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class DeleteScriptServiceImpl implements IDeleteScriptService {

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private final IScriptRepository scriptRepository;
  private final ILogRepository logRepository;
  private final IUserRepository userRepository;
  private final IFileStorageService fileStorageService;

  @Override
  public void deleteScript(ScriptDto scriptDto) {
    if (scriptRepository.findById(scriptDto.getId()).isEmpty()) {
      throw new RuntimeException("Script not found!");
    }
    if (userRepository.findByUsername(scriptDto.getUser()).isEmpty()) {
      throw new RuntimeException("User not found!");
    }
    if (!userRepository.findByUsername(scriptDto.getUser()).get().getRole().getName()
        .equals(ROLE_ADMIN)) {
      throw new RuntimeException("User is not admin!");
    }

    logRepository.deleteAllByScriptId(scriptDto.getId());

    Script script = scriptRepository.findById(scriptDto.getId()).get();
    try {
      fileStorageService.delete(script.getFile());
    } catch (Exception e) {
      throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
    }

    scriptRepository.delete(script);
  }
}
