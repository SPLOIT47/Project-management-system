package com.crm.application.controller;

import com.crm.application.service.UserService;
import com.crm.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Stream<String> getUsersSuggestions(String text, List<String> excludeUsernames, int limit) {
        return this.userService.getSuggestionByPrefix(text, excludeUsernames, limit).stream().map(User::getUsername);
    }
}
