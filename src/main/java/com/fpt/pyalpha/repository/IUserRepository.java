package com.fpt.pyalpha.repository;

import com.fpt.pyalpha.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  @Query("SELECT user FROM User user WHERE user.role.id = :roleId")
  List<User> findAllByRoleId(@Param(value = "roleId") Long roleId);
}
