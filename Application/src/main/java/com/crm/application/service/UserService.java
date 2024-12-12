package com.crm.application.service;

import com.crm.application.dto.UserDTO;
import com.crm.domain.entity.Notification;
import com.crm.domain.entity.User;
import com.crm.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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

    public Stream<User> mergeUsers(Collection<UserDTO> newUsers, Collection<User> oldUsers) {
        Collection<User> mergedUsers = new ArrayList<>();

        newUsers.stream()
                .filter(user -> !newUsers.stream()
                        .map(UserDTO::username)
                        .toList()
                        .contains(user.username()))
                .forEach(userDto -> {
                    User user = new User(
                            userDto.id(),
                            userDto.username(),
                            userDto.password()
                    );
                    mergedUsers.add(user);
                });

        oldUsers.stream().filter(user -> !newUsers.stream()
                    .map(UserDTO::username)
                    .toList()
                    .contains(user.getUsername()))
                .forEach(mergedUsers::remove);

        return mergedUsers.stream();
    }
}
