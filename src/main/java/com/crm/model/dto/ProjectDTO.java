package com.crm.model.dto;

import com.crm.model.entity.AppUser;
import com.crm.model.entity.Task;

import java.util.Collection;
import java.util.UUID;

public record ProjectDTO(
        UUID id,
        String projectName,
        String role,
        String username,
        String description,
        Collection<AppUser> users,
        Collection<Task> tasks) {
}
