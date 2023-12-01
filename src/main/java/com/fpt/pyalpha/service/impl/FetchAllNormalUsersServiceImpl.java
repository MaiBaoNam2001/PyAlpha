package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.mapper.IUserMapper;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IFetchAllNormalUsersService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchAllNormalUsersServiceImpl implements IFetchAllNormalUsersService {

  private final IUserRepository userRepository;

  @Override
  public List<UserDto> fetchAllNormalUsers() {
    return ListUtils.emptyIfNull(userRepository.findAllByRoleId(2L)).stream()
        .map(IUserMapper.INSTANCE::toUserDto)
        .sorted(Comparator.comparingLong(UserDto::getId).reversed()).collect(Collectors.toList());
  }
}
