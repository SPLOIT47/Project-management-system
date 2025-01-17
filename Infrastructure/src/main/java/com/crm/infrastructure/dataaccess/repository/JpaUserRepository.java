package com.crm.infrastructure.dataaccess.repository;

import com.crm.domain.entity.User;
import com.crm.domain.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends CrudRepository<User, UUID>, UserRepository {
    @Override
    @Query("SELECT u from User u where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Override
    @Query("SELECT DISTINCT u " +
            "FROM User u " +
            "WHERE u.username IN (:usernames)")
    List<User> findAllByUsername(@Param("usernames") List<String> username);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.username LIKE CONCAT(:prefix, '%') " +
            "AND (:excludedUsernames IS NULL OR u.username NOT IN :excludedUsernames) " +
            "ORDER BY u.username")
    List<User> findByPrefixExcludingUsernames(@Param("prefix") String prefix,
                                              @Param("excludedUsernames") List<String> excludedUsernames,
                                              Pageable pageable);
}
