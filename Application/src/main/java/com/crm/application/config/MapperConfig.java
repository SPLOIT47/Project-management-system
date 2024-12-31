package com.crm.application.config;

import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.TaskDto;
import com.crm.application.dto.UserDTO;
import com.crm.application.mapper.Mapper;
import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import com.crm.domain.entity.mapping.UserRoleMapping;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class MapperConfig {
    @PostConstruct
    public void init() {
        Mapper.createMapper(User.class, UserDTO.class, (User user) -> new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        ));

        Mapper.createMapper(Task.class, TaskDto.class, (Task task) -> new TaskDto(
                task.getName(),
                task.getDescription(),
                task.getProject().getName(),
                task.getStatus().toString(),
                task.getPriority(),
                task.getAssignedUsers()
                        .stream()
                        .map(user -> Mapper.map(user, UserDTO.class))
                        .toList()
        ));

        Mapper.createMapper(Project.class, ProjectDTO.class, (Project project) -> new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getManager().getUsername(),
                project.getProjectRoles().stream()
                        .collect(Collectors.toMap(
                                user -> Mapper.map(user.getUser(), UserDTO.class),
                                UserRoleMapping::getRole,
                                (existing, replacement) -> existing
                        )),
                project.getTasks().stream().map(task -> Mapper.map(task, TaskDto.class)).toList()
        ));
    }
}
