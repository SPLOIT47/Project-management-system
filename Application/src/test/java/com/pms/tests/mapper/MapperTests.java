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
        assertEquals(userDTO.id(), id);
        assertEquals(userDTO.password(), user.getPassword());
        assertEquals(userDTO.username(), user.getUsername());
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
        // Создаем менеджера
        User manager = new User("manager", "password");

        // Создаем проект и передаем менеджера
        Project project = new Project("project", "some project", manager);

        // Создаем пользователей
        User user1 = new User("user", "password");
        User user2 = new User("user2", "password2");

        // Добавляем пользователей в проект
        project.addUser(user1, UserRole.Employee);
        project.addUser(user2, UserRole.Employee);

        assertNotNull(project.getManager());

        // Проверяем маппинг
        ProjectDTO projectDTO = Mapper.map(project, ProjectDTO.class);

        // Проверяем, что все поля проекта корректно замаплены
        assertEquals(projectDTO.projectName(), project.getName());
        assertEquals(projectDTO.description(), project.getDescription());
        assertEquals(projectDTO.managerName(), project.getManager().getUsername()); // Добавлена проверка менеджера

        // Проверяем маппинг пользователей и их ролей
        for (var obj : projectDTO.users().entrySet()) {
            Optional<UserRoleMapping> user = project.getProjectRoles().stream().filter(mapping
                            -> mapping
                            .getUser()
                            .getUsername()
                            .equals(obj.getKey().username()))
                    .findFirst();

            assertTrue(user.isPresent());
            assertEquals(user.get().getUser().getUsername(), obj.getKey().username());
            assertEquals(user.get().getUser().getPassword(), obj.getKey().password());
            assertEquals(user.get().getRole(), obj.getValue());
        }
    }
}
