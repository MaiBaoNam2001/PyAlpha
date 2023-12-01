package com.fpt.pyalpha.repository;

import com.fpt.pyalpha.entity.Script;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScriptRepository extends JpaRepository<Script, Long> {

  Optional<Script> findByTitle(String title);

  Optional<Script> findByFile(String file);
}
