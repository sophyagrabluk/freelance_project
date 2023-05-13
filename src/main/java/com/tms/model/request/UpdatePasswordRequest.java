package com.tms.model.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdatePasswordRequest {

    private int id;

    @Size(min = 8, max = 100)
    private String oldPassword;

    @Size(min = 8, max = 100)
    private String newPassword;
}
