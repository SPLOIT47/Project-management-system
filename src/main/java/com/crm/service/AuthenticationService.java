package com.crm.service;

import com.crm.model.dto.UserDTO;
import com.crm.model.entity.AppUser;
import com.crm.model.repository.UserRepository;
import com.crm.utils.PasswordEncoder;
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

    public AppUser registerUser(UserDTO userDTO) {
        AppUser appUser = new AppUser();
        appUser.setUsername(userDTO.username());
        appUser.setPassword(userDTO.password());

        return this.userRepository.save(appUser);
    }

    public Optional<AppUser> AuthenticateUser(UserDTO user) {
        Optional<AppUser> existingOptionalUser = this.userRepository.findUserByUsername(user.username());
        if (existingOptionalUser.isEmpty()) {
            return Optional.empty();
        }

        AppUser existingAppUser = existingOptionalUser.get();

        if (!PasswordEncoder.Equals(user.password(), existingAppUser.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(existingAppUser);
    }
}
