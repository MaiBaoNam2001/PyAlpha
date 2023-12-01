package com.fpt.pyalpha.repository;

import com.fpt.pyalpha.entity.Log;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogRepository extends JpaRepository<Log, Long> {

  @Query("SELECT log FROM Log log WHERE log.script.id = :scriptId")
  List<Log> findAllByScriptId(@Param(value = "scriptId") Long scriptId);

  @Modifying
  @Query("DELETE FROM Log log WHERE log.script.id = :scriptId")
  void deleteAllByScriptId(@Param(value = "scriptId") Long scriptId);

  @Modifying
  @Query("DELETE FROM Log log WHERE log.user.id = :userId")
  void deleteAllByUserId(@Param(value = "userId") Long userId);
}
