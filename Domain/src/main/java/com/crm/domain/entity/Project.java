package com.crm.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import java.util.HashSet;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany
    private Collection<Task> tasks;

    @ManyToOne
    private User manager;

    @ManyToMany
    private Collection<User> customers;

    public Project(String name, String description, User manager) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.customers = new HashSet<>();
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void addCustomer(User user) {
        this.customers.add(user);
    }

    public void removeCustomer(User user) {
        this.customers.remove(user);
    }
}