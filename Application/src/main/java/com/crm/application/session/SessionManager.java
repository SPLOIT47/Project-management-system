package com.crm.application.session;

import com.crm.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SessionManager {
    private User currentUser;

    public String getCurrentUserName() {
        return this.currentUser.getUsername();
    }
}
