package com.crm.service;

import com.crm.model.dto.UserDTO;
import com.crm.model.entity.user.AppUser;
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

    public void registerUser(AppUser appUser) {
        this.userRepository.save(appUser);
    }

    public boolean AuthenticateUser(UserDTO user) {
        Optional<AppUser> existingOptionalUser = this.userRepository.findUserByUsername(user.username());
        if (existingOptionalUser.isEmpty()) {
            return false;
        }

        AppUser existingAppUser = existingOptionalUser.get();

        return PasswordEncoder.Equals(user.password(), existingAppUser.getPassword());
    }
}
