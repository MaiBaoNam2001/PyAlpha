package com.fpt.pyalpha.mapper;

import com.fpt.pyalpha.dto.UserDto;
import com.fpt.pyalpha.entity.Role;
import com.fpt.pyalpha.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {

  IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

  @Mapping(source = "role", target = "role")
  UserDto toUserDto(User user);

  default String mapRoleToString(Role role) {
    return role.getName();
  }
}
