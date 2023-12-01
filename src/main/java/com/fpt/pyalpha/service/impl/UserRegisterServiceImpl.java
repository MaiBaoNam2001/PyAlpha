package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.entity.User;
import com.fpt.pyalpha.repository.IRoleRepository;
import com.fpt.pyalpha.repository.IUserRepository;
import com.fpt.pyalpha.service.IUserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements IUserRegisterService {

  private final IUserRepository userRepository;
  private final IRoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void registerUser(UserDto userDto) {
    if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
      throw new RuntimeException("Username is already exist!");
    }
    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(roleRepository.findByName(userDto.getRole())
        .orElseThrow(() -> new RuntimeException("Role is not exist!")));
    userRepository.save(user);
  }
}
