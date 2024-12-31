package com.crm.domain.entity;


import com.crm.domain.enums.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Project project;

    private String Name;

    private String Description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private int priority;

    @ManyToMany
    private Collection<User> assignedUsers = new ArrayList<>();

    public Task(Project project, String name, String description, int priority) {
        this.id = UUID.randomUUID();
        this.project = project;
        this.Name = name;
        this.Description = description;
        this.status = TaskStatus.TODO;
        this.priority = priority;
    }
}
