package com.spring.bookstore.event;

import com.spring.bookstore.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegisterCompleteEvent extends ApplicationEvent {

    private String appURL;
    private Users users;

    public OnRegisterCompleteEvent(Users users, String appURL) {
        super(users);

        this.users = users;
        this.appURL = appURL;
    }
}
