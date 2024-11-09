package com.crm.model.repository;

import com.crm.model.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface NotificationRepository extends CrudRepository<Notification, UUID> {
}
