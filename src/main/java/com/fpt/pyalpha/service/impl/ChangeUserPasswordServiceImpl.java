package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.entity.User;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IChangeUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeUserPasswordServiceImpl implements IChangeUserPasswordService {

  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public void changeUserPassword(UserDto userDto) {
    if (userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
      throw new RuntimeException("Username is not exist!");
    }
    if (StringUtils.isEmpty(userDto.getPassword())) {
      throw new RuntimeException("Password is empty!");
    }
    if (bCryptPasswordEncoder.matches(userDto.getPassword(),
        userRepository.findByUsername(userDto.getUsername()).get().getPassword())) {
      throw new RuntimeException("New password is the same as old password!");
    }

    User user = userRepository.findByUsername(userDto.getUsername()).get();
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userRepository.save(user);
  }
}
