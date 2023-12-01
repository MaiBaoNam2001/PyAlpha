package com.fpt.pyalpha.service;

import com.fpt.pyalpha.dto.UserDto;

public interface IChangeUserPasswordService {

  void changeUserPassword(UserDto userDto);
}
