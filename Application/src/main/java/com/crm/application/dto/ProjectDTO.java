package com.crm.application.dto;

import java.util.Collection;
import java.util.UUID;

public record ProjectDTO(
        UUID id,
        String projectName,
        String managerName,
        String description,
        Collection<UserDTO> users,
        Collection<TaskDto> tasks) {
}
