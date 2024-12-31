package com.crm.domain.service;

import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import com.crm.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserManager {
    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password) {
        String encodedPassword = PasswordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        return this.userRepository.save(user);
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public Optional<User> updateUserTasks(UUID id, Collection<Task> tasks) {
        Optional<User> user = this.userRepository.findById(id);

        user.ifPresent(value -> {
            value.setTasks(tasks);
            this.userRepository.save(value);
        });

        return user;
    }
}
