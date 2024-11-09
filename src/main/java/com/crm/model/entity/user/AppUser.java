package com.crm.model.entity.user;

import com.crm.model.entity.Notification;
import com.crm.model.entity.Task;
import com.crm.model.observer.Observer;
import com.crm.model.repository.TaskRepository;
import com.crm.model.repository.UserRepository;
import com.crm.service.NotificationService;
import com.crm.service.ProjectService;
import com.crm.utils.PasswordEncoder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Column(unique = true)
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
