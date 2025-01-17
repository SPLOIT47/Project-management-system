package com.crm.application.session;

import com.crm.domain.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Data
public class SessionManager {
    private User currentUser;

    public String getCurrentUserName() {
        return this.currentUser.getUsername();
    }
}
