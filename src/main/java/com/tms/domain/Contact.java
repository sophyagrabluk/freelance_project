package com.tms.domain;

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
