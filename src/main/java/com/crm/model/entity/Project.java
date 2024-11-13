package com.crm.model.entity;

import com.crm.model.dto.ProjectDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @OneToMany
    private Collection<Task> tasks;

    @ManyToOne
    private AppUser manager;

    @ManyToMany
    private Collection<AppUser> customers;

    public Project(ProjectDTO projectDTO) {
        this.name = projectDTO.projectName();
        this.description = projectDTO.description();
        this.customers = projectDTO.users();
        this.tasks = projectDTO.tasks();
    }
}
