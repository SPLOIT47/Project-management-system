package com.crm.application.dto;

import java.util.UUID;

public record UserDTO(UUID id, String username, String password) {

    public UserDTO(String username, String password) {
        this(UUID.randomUUID(), username, password);
    }
}
