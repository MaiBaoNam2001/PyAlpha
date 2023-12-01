package com.fpt.pyalpha.service;

import com.fpt.pyalpha.dto.UserDto;
import java.util.List;

public interface IFetchAllNormalUsersService {

  List<UserDto> fetchAllNormalUsers();
}
