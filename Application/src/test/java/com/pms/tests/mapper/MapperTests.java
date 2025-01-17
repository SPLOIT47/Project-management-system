package com.pms.tests.mapper;

import com.crm.application.config.MapperConfig;
import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.TaskDto;
import com.crm.application.dto.UserDTO;
import com.crm.application.mapper.Mapper;
import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import com.crm.domain.entity.mapping.UserRoleMapping;
import com.crm.domain.enums.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

public class MapperTests {
    @BeforeAll
    public static void setUp() {
        MapperConfig config = new MapperConfig();
        config.init();
    }

    @Test
    public void testUserMapping() {
        User user = new User("user", "password");
        UUID id = user.getId();

        UserDTO userDTO = Mapper.map(user, UserDTO.class);
        assertEquals(userDTO.getId(), id);
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getUsername(), user.getUsername());
    }

    @Test
    public void testTaskMapping() {
        User manager = new User("manager", "password");
        Project project = new Project("project", "some project", manager);
        Task task = new Task(project, "task", "description", 124);

        TaskDto taskDto = Mapper.map(task, TaskDto.class);

        assertEquals(taskDto.projectName(), project.getName());
        assertEquals(taskDto.taskName(), task.getName());
        assertEquals(taskDto.description(), task.getDescription());
        assertEquals(taskDto.priority(), task.getPriority());
        assertEquals(taskDto.status(), task.getStatus().toString());
    }

    @Test
    public void testProjectMapping() {
        User manager = new User("manager", "password");

        Project project = new Project("project", "some project", manager);

        User user1 = new User("user", "password");
        User user2 = new User("user2", "password2");

        project.addUser(user1, UserRole.Employee);
        project.addUser(user2, UserRole.Employee);

        assertNotNull(project.getManager());

        ProjectDTO projectDTO = Mapper.map(project, ProjectDTO.class);

        assertEquals(projectDTO.getProjectName(), project.getName());
        assertEquals(projectDTO.getDescription(), project.getDescription());
        assertEquals(projectDTO.getManagerName(), project.getManager().getUsername());

        for (var obj : projectDTO.getUsers().entrySet()) {
            Optional<UserRoleMapping> user = project.getProjectRoles().stream().filter(mapping
                            -> mapping
                            .getUser()
                            .getUsername()
                            .equals(obj.getKey()))
                    .findFirst();

            assertTrue(user.isPresent());
            assertEquals(user.get().getUser().getUsername(), obj.getKey());
            assertEquals(user.get().getRole(), obj.getValue());
        }
    }
}
