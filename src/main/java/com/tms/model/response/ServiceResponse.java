package com.tms.model.response;

import com.tms.utils.SectionType;
import lombok.Data;

@Data
public class ServiceResponse {

    private  String name;
    private SectionType section;
    private String description;
    private double rating;
}
