package com.crm.domain.repository;

import com.crm.domain.entity.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository {
    Notification save(Notification notification);
}
