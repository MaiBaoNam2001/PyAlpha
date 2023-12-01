package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.mapper.IScriptMapper;
import com.fpt.pyalpha.repository.IScriptRepository;
import com.fpt.pyalpha.service.IFetchAllScriptsService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchAllScriptsServiceImpl implements IFetchAllScriptsService {

  private final IScriptRepository scriptRepository;

  @Override
  public List<ScriptDto> fetchAllScripts() {
    return ListUtils.emptyIfNull(scriptRepository.findAll()).stream()
        .map(IScriptMapper.INSTANCE::toScriptDto)
        .sorted(Comparator.comparingLong(ScriptDto::getId).reversed()).collect(Collectors.toList());
  }
}
