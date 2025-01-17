package com.crm.application.service;

import com.crm.application.dto.UserDTO;
import com.crm.domain.service.PasswordEncoder;
import com.crm.domain.entity.User;
import com.crm.domain.repository.UserRepository;
import com.crm.domain.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserManager userManager;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(
            UserManager userManager,
            UserRepository userRepository) {
        this.userManager = userManager;
        this.userRepository = userRepository;
    }

    public User registerUser(UserDTO userDTO) {
        return this.userManager.createUser(userDTO.getUsername(), userDTO.getPassword());
    }

    public Optional<User> AuthenticateUser(UserDTO user) {
        Optional<User> existingOptionalUser = this.userRepository.findUserByUsername(user.getUsername());
        if (existingOptionalUser.isEmpty()) {
            return Optional.empty();
        }

        User existingAppUser = existingOptionalUser.get();

        if (!PasswordEncoder.Equals(user.getPassword(), existingAppUser.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(existingAppUser);
    }
}
