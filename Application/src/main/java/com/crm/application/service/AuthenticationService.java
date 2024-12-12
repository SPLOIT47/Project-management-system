package com.crm.application.service;

import com.crm.application.dto.UserDTO;
import com.crm.domain.service.PasswordEncoder;
import com.crm.domain.entity.User;
import com.crm.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserDTO userDTO) {
        User appUser = new User();
        appUser.setUsername(userDTO.username());
        appUser.setPassword(userDTO.password());

        return this.userRepository.save(appUser);
    }

    public Optional<User> AuthenticateUser(UserDTO user) {
        Optional<User> existingOptionalUser = this.userRepository.findUserByUsername(user.username());
        if (existingOptionalUser.isEmpty()) {
            return Optional.empty();
        }

        User existingAppUser = existingOptionalUser.get();

        if (!PasswordEncoder.Equals(user.password(), existingAppUser.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(existingAppUser);
    }
}
