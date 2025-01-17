package com.crm.application.dto;

import com.crm.domain.enums.UserRole;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public record ProjectDTO(
        UUID id,
        String projectName,
        String description,
        String managerName,
        Map<String, UserRole> users,
        Collection<TaskDto> tasks) {
}
