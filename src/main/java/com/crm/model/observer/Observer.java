package com.crm.model.observer;


import com.crm.model.entity.Notification;
import com.crm.model.entity.Task;

public interface Observer {
    void update(Notification message);
}
