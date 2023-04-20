package com.tms.service;

import com.tms.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServiceService {

    ServiceRepository serviceRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public com.tms.model.Service getServiceById(int id) {
        try {
            return serviceRepository.getServiceById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<com.tms.model.Service> getAllServices() {
        return serviceRepository.getAllServices();
    }

    public ArrayList<com.tms.model.Service> getServiceFromOneUser(int userId) {
        return serviceRepository.getServiceFromOneUser(userId);
    }

    public ArrayList<com.tms.model.Service> getServicesFromOneSection(String section) {
        return serviceRepository.getServicesFromOneSection(section);
    }

    public boolean createService(com.tms.model.Service service) {
        try {
            return serviceRepository.createService(service);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return false;
        }
    }

    public boolean updateService(com.tms.model.Service service) {
        try {
            return serviceRepository.updateService(service);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteService(int id) {
        return serviceRepository.deleteService(id);
    }
}