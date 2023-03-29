package com.tms.domain;

import lombok.Data;
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

}
