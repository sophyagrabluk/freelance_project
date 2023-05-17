package com.tms.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionInfo {

    private String message;
    private int statusCode;
    private String statusMessage;
}