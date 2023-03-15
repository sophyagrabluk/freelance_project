package com.tms.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class Service {
    int id;
    String name;
    String section;
    int usersId;
    String description;
    boolean isDeleted;

    private User user;

    @Autowired
    public Service(User user) {
        this.user = user;
    }
}
