package com.crm.application.dto;

import java.util.Collection;

public record TaskDto(
        String taskName,
        String description,
        String projectName,
        String status,
        int priority,
        Collection<UserDTO> assignedUsers) {
}
