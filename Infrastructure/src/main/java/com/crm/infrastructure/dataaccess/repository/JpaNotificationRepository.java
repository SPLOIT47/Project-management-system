package com.crm.infrastructure.dataaccess.repository;

import com.crm.domain.entity.Notification;
import com.crm.domain.repository.NotificationRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JpaNotificationRepository extends CrudRepository<Notification, UUID>, NotificationRepository {
}
