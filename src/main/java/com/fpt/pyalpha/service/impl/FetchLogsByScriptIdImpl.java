package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.LogDto;
import com.fpt.pyalpha.mapper.ILogMapper;
import com.fpt.pyalpha.repository.ILogRepository;
import com.fpt.pyalpha.service.IFetchLogsByScriptIdService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchLogsByScriptIdImpl implements IFetchLogsByScriptIdService {

  private final ILogRepository logRepository;

  @Override
  public List<LogDto> fetchLogsByScriptId(Long scriptId) {
    return ListUtils.emptyIfNull(logRepository.findAllByScriptId(scriptId)).stream()
        .map(ILogMapper.INSTANCE::toLogDto)
        .sorted(Comparator.comparingLong(LogDto::getId).reversed()).collect(Collectors.toList());
  }
}
