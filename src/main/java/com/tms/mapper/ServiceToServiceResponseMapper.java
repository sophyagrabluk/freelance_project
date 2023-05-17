package com.tms.mapper;

import com.tms.model.Service;
import com.tms.model.response.ServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class ServiceToServiceResponseMapper {

    public ServiceResponse serviceToResponse(Service service){
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setName(service.getName());
        serviceResponse.setSection(service.getSection());
        serviceResponse.setDescription(service.getDescription());
        serviceResponse.setRating(service.getRating());
        return serviceResponse;
    }
}