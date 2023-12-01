package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.entity.Script;
import com.fpt.pyalpha.mapper.IScriptMapper;
import com.fpt.pyalpha.repository.IScriptRepository;
import com.fpt.pyalpha.service.IFetchScriptByIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchScriptByIdServiceImpl implements IFetchScriptByIdService {

  private final IScriptRepository scriptRepository;

  @Override
  public ScriptDto fetchScriptById(Long id) {
    Script script = scriptRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Script not found!"));
    return IScriptMapper.INSTANCE.toScriptDto(script);
  }
}
