package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.repository.ILogRepository;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IDeleteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class DeleteUserServiceImpl implements IDeleteUserService {

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private final ILogRepository logRepository;
  private final IUserRepository userRepository;

  @Override
  public void deleteUser(UserDto userDto) {
    if (userRepository.findById(userDto.getId()).isEmpty()) {
      throw new RuntimeException("User is not exist!");
    }
    if (userRepository.findById(userDto.getId()).get().getRole().getName().equals(ROLE_ADMIN)) {
      throw new RuntimeException("Can not delete admin!");
    }

    logRepository.deleteAllByUserId(userDto.getId());
    userRepository.deleteById(userDto.getId());
  }
}
