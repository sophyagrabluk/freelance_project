package com.tms.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
@Data
public class Service {
    int id;
    String name;
    @Pattern(regexp = "[A-z]*")
    String section;
    String description;
    boolean isDeleted;
    int userId;
}