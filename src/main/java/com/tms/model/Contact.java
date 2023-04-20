package com.tms.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Contact {
    int id;
    String email;
    String phoneNumber;
    String telegram;
}