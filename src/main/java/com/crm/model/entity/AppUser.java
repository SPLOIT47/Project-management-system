package com.crm.model.entity;

import com.crm.model.observer.Observer;
import com.crm.utils.PasswordEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity
public class AppUser implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @ManyToMany
    private Collection<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Notification> notifications = new ArrayList<>();;

    @Override
    public void update(Notification notification) {
        this.notifications.add(notification);
    }

    public void setPassword(String password) {
        this.password = PasswordEncoder.encode(password);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }
}
