package com.crm.application.service;

import com.crm.domain.entity.Notification;
import com.crm.domain.entity.User;
import com.crm.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<User> getSuggestionByPrefix(String prefix, List<String> excludedUsernames, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return this.userRepository.findByPrefixExcludingUsernames(prefix, excludedUsernames, pageable);
    }
}