package com.tms.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {

    private int id;

    @Size(min = 8, max = 100)
    private String oldPassword;

    @Size(min = 8, max = 100)
    private String newPassword;
}
