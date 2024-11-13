package com.crm.service;

import com.crm.model.entity.Notification;
import com.crm.model.entity.AppUser;
import com.crm.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final UserRepository userRepository;

    @Autowired
    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser updateUser(AppUser user, Notification notification) {
        user.update(notification);
        return this.userRepository.save(user);
    }
}
