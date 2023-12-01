package com.fpt.pyalpha.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "log")
public class Log {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "execute_time")
  private Instant executeTime;

  @Column(name = "cpu_time")
  private Integer cpuTime;

  @Column(name = "status")
  private Integer status;

  @Column(name = "input")
  @Type(type = "org.hibernate.type.TextType")
  private String input;

  @Column(name = "output")
  @Type(type = "org.hibernate.type.TextType")
  private String output;

  @Column(name = "error")
  @Type(type = "org.hibernate.type.TextType")
  private String error;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "script_id")
  private Script script;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public Instant getExecuteTime() {
    return executeTime;
  }

  public void setExecuteTime(Instant executeTime) {
    this.executeTime = executeTime;
  }

  public Integer getCpuTime() {
    return cpuTime;
  }

  public void setCpuTime(Integer cpuTime) {
    this.cpuTime = cpuTime;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Script getScript() {
    return script;
  }

  public void setScript(Script script) {
    this.script = script;
  }

}