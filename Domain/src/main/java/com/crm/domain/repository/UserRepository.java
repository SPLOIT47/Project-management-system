package com.crm.domain.repository;

import com.crm.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository  {

    User save(User user);

    Optional<User> findById(UUID id);

    @Query("SELECT u from appuser u where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("SELECT DISTINCT u " +
            "from appuser u " +
            "where u.username in (:users)")
    List<User> findAllByUsername(@Param("users") List<String> username);
}
