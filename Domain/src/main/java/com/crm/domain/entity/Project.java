package com.crm.domain.entity;


import com.crm.domain.entity.mapping.UserRoleMapping;
import com.crm.domain.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

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

    @ManyToOne()
    private User manager;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Task> tasks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleMapping> projectRoles;

    public Project(String name, String description, User manager) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.manager = manager;
        this.description = description;
        this.tasks = new ArrayList<>();
        this.projectRoles = new HashSet<>();
        this.projectRoles.add(new UserRoleMapping(manager, this, UserRole.Manager));
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void addUser(User user, UserRole userRole) {
       this.projectRoles.add(new UserRoleMapping(user, this, userRole));
    }

    public void removeUser(User user) {
        Iterator<UserRoleMapping> iterator = this.projectRoles.iterator();
        while (iterator.hasNext()) {
            UserRoleMapping role = iterator.next();
            if (role.getUser().equals(user)) {
                if (role.getRole() != UserRole.Manager) {
                    iterator.remove();
                } else {
                    throw new RuntimeException("Manager cannot be removed");
                }
                break;
            }
        }
    }

}