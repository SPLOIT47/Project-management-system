package com.crm.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String username;
    private String password;

    public UserDTO(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }
}
