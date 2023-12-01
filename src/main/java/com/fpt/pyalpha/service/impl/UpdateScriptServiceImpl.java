package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.entity.Script;
import com.fpt.pyalpha.repository.IScriptRepository;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IUpdateScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateScriptServiceImpl implements IUpdateScriptService {

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private final IUserRepository userRepository;
  private final IScriptRepository scriptRepository;

  @Override
  public void updateScript(ScriptDto scriptDto) {
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
    if (scriptRepository.findByTitle(scriptDto.getTitle()).isPresent()) {
      throw new RuntimeException("Script title already exists!");
    }
    Script script = scriptRepository.findById(scriptDto.getId()).get();
    script.setTitle(scriptDto.getTitle());
    script.setDescription(scriptDto.getDescription());
    scriptRepository.save(script);
  }
}
