package com.crm.model.repository;

import com.crm.model.entity.user.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<AppUser, UUID> {
    Optional<AppUser> findUserByUsername(String username);
}
