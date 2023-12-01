package com.fpt.pyalpha.service.impl;

import com.fpt.pyalpha.dto.LogDto;
import com.fpt.pyalpha.repository.IScriptRepository;
import com.fpt.pyalpha.service.IExecuteScriptService;
import com.fpt.pyalpha.service.IFileStorageService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecuteScriptServiceImpl implements IExecuteScriptService {

  private static final String PYTHON_INPUT = "input()";
  private final IFileStorageService fileStorageService;
  private final IScriptRepository scriptRepository;

  @Override
  public LogDto execute(LogDto logDto) {
    if (StringUtils.isEmpty(logDto.getFile())) {
      throw new RuntimeException("File is empty!");
    }
    if (Objects.isNull(logDto.getInput())) {
      throw new RuntimeException("Input is null!");
    }
    if (scriptRepository.findByFile(logDto.getFile()).isEmpty()) {
      throw new RuntimeException("File not found!");
    }
    Resource resource = fileStorageService.load(logDto.getFile());
    String script = null;
    try {
      script = new String(resource.getInputStream().readAllBytes());
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("File content is empty!");
    }
    try {
      long startTime = System.nanoTime();
      ProcessBuilder processBuilder = new ProcessBuilder("py", "-c", script);
      Map<String, String> env = processBuilder.environment();
      env.put("PYTHONIOENCODING", "UTF-8");
      env.put("PYTHONUNBUFFERED", "1");
      Process process = processBuilder.start();

      if (script.contains(PYTHON_INPUT)) {
        String input = logDto.getInput();
        if (StringUtils.isEmpty(input)) {
          throw new RuntimeException("Script requires input but input field is empty!");
        }
        OutputStream stdinStream = process.getOutputStream();
        stdinStream.write(input.getBytes());
        stdinStream.close();
      }

      InputStream stdoutStream = process.getInputStream();
      InputStream stderrStream = process.getErrorStream();

      String stdout = new BufferedReader(new InputStreamReader(stdoutStream)).lines()
          .collect(Collectors.joining("\n"));
      String stderr = new BufferedReader(new InputStreamReader(stderrStream)).lines()
          .collect(Collectors.joining("\n"));
      int exitCode = process.waitFor();
      long endTime = System.nanoTime();
      long cpuTime = endTime - startTime;
      logDto.setExecuteTime(Instant.now());
      logDto.setCpuTime((int) TimeUnit.NANOSECONDS.toMillis(cpuTime));
      logDto.setStatus(exitCode == 0 ? 200 : 400);
      logDto.setInput(logDto.getInput());
      logDto.setOutput(stdout);
      if (exitCode != 0) {
        logDto.setError(stderr);
      }
      logDto.setScript(logDto.getScript());
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      logDto.setError(e.getMessage());
      return logDto;
    }
    return logDto;
  }
}
