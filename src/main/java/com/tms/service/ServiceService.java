package com.tms.service;

import com.tms.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServiceService {

//    {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public com.tms.domain.Service getServiceById(int id) {
        return serviceRepository.getServiceById(id);
    }

    public ArrayList<com.tms.domain.Service> getAllServices() {
        return serviceRepository.getAllServices();
    }

    public ArrayList<com.tms.domain.Service> getServiceFromOneUser(int userId) {
        return serviceRepository.getServiceFromOneUser(userId);
    }

    public boolean createService(com.tms.domain.Service service) {
       return serviceRepository.createService(service);
    }

    public boolean updateService(com.tms.domain.Service service) {
        return serviceRepository.updateService(service);
    }

    public boolean deleteService(int id) {
        return serviceRepository.deleteService(id);
    }
}