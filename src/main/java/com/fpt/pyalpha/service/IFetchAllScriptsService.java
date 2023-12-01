package com.fpt.pyalpha.service;

import com.fpt.pyalpha.dto.ScriptDto;
import java.util.List;

public interface IFetchAllScriptsService {

  List<ScriptDto> fetchAllScripts();
}
