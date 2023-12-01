package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.entity.Script;
import com.fpt.pyalpha.repository.IScriptRepository;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IFileStorageService;
import com.fpt.pyalpha.service.IAddScriptService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AddScriptServiceImpl implements IAddScriptService {

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private final IUserRepository userRepository;
  private final IScriptRepository scriptRepository;
  private final IFileStorageService fileStorageService;

  @Override
  public void addScript(ScriptDto scriptDto, MultipartFile file) {
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
    if (Objects.isNull(file) || file.isEmpty()) {
      throw new RuntimeException("File is empty!");
    }
    if (scriptRepository.findByFile(file.getOriginalFilename()).isPresent()) {
      throw new RuntimeException("Script file already exists!");
    }
    try {
      fileStorageService.store(file);
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }

    Script script = new Script();
    script.setTitle(scriptDto.getTitle());
    script.setFile(file.getOriginalFilename());
    script.setDescription(scriptDto.getDescription());
    script.setUser(userRepository.findByUsername(scriptDto.getUser()).get());
    scriptRepository.save(script);
  }
}
