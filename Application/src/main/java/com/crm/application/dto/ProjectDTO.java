package com.crm.application.dto;

import com.crm.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProjectDTO {
    private UUID id;
    private String projectName;
    private String description;
    private String managerName;
    private Map<String, UserRole> users;
    private Collection<TaskDto> tasks;
}
