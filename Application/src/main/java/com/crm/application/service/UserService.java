package com.crm.application.service;

import com.crm.application.dto.UserDTO;
import com.crm.domain.entity.Notification;
import com.crm.domain.entity.User;
import com.crm.domain.entity.mapping.UserRoleMapping;
import com.crm.domain.enums.UserRole;
import com.crm.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUser(User user, Notification notification) {
//        user.update(notification);
        return this.userRepository.save(user);
    }

    public Stream<UserRoleMapping> mergeUserRoles(Map<UserDTO, UserRole> newUserRoles, Collection<UserRoleMapping> oldUserRoles) {
        Collection<UserRoleMapping> mergedUserRoles = new ArrayList<>(oldUserRoles);

        newUserRoles.forEach((userDto, userRole) -> {
            UserRoleMapping existingMapping = mergedUserRoles.stream()
                    .filter(mapping -> mapping.getUser().getUsername().equals(userDto.username()))
                    .findFirst()
                    .orElse(null);

            if (existingMapping != null) {
                existingMapping.setRole(userRole);
            } else {
                User user = new User(userDto.id(), userDto.username(), userDto.password());
                UserRoleMapping newMapping = new UserRoleMapping();
                newMapping.setUser(user);
                newMapping.setRole(userRole);
                mergedUserRoles.add(newMapping);
            }
        });

        mergedUserRoles.removeIf(mapping ->
                newUserRoles.keySet().stream()
                        .map(UserDTO::username)
                        .noneMatch(username -> username.equals(mapping.getUser().getUsername()))
        );

        return mergedUserRoles.stream();
    }
}
