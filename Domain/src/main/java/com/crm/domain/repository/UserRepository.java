package com.crm.domain.repository;

import com.crm.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);

    @Query("SELECT DISTINCT u " +
            "from User u " +
            "where u.username in (:users)")
    List<User> findAllByUsername(@Param("users") List<String> username);
}
