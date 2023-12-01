package com.fpt.pyalpha.mapper;

import com.fpt.pyalpha.dto.ScriptDto;
import com.fpt.pyalpha.entity.Script;
import com.fpt.pyalpha.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IScriptMapper {

  IScriptMapper INSTANCE = Mappers.getMapper(IScriptMapper.class);

  @Mapping(source = "user", target = "user")
  ScriptDto toScriptDto(Script script);

  default String mapUserToString(User user) {
    return user.getUsername();
  }

}
