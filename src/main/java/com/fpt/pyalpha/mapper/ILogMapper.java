package com.fpt.pyalpha.mapper;

import com.fpt.pyalpha.dto.LogDto;
import com.fpt.pyalpha.entity.Log;
import com.fpt.pyalpha.entity.Script;
import com.fpt.pyalpha.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ILogMapper {

  ILogMapper INSTANCE = Mappers.getMapper(ILogMapper.class);

  @Mapping(source = "script", target = "script")
  @Mapping(source = "user", target = "username")
  LogDto toLogDto(Log log);

  default Long mapScriptToLong(Script script) {
    return script.getId();
  }

  default String mapUserToString(User user) {
    return user.getUsername();
  }
}
