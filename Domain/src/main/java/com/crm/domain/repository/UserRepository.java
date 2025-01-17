package com.crm.domain.repository;

import com.crm.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository  {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findUserByUsername(@Param("username") String username);

    List<User> findAllByUsername(@Param("users") List<String> username);

    List<User> findByPrefixExcludingUsernames(@Param("prefix") String prefix,
                                              @Param("excludedUsernames") List<String> excludedUsernames,
                                              Pageable pageable);
}
