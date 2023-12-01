package com.fpt.pyalpha.repository;

import com.fpt.pyalpha.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
