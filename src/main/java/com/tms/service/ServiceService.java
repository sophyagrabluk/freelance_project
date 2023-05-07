package com.tms.service;

import com.tms.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

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
            Optional<com.tms.model.Service> service = serviceRepository.findById(id);
            if (service.isPresent() && !service.get().isDeleted()) {
                return service.orElse(null);
            }
            return null;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<com.tms.model.Service> getAllServices() {
        return (ArrayList<com.tms.model.Service>) serviceRepository.findAll();
    }

    public ArrayList<com.tms.model.Service> findServiceByUserId(int userId) {
        return serviceRepository.findServiceByUserId(userId).orElse(new ArrayList<>());
    }

    public ArrayList<com.tms.model.Service> findServiceBySection(String section) {
        return serviceRepository.findServiceBySection(section).orElse(new ArrayList<>());
    }

    public ArrayList<com.tms.model.Service> getAllServicesFromHighestRating() {
        return serviceRepository.findServicesByOrderByRatingDesc();
    }

    public com.tms.model.Service createService(com.tms.model.Service service) {
        try {
            return serviceRepository.save(service);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    public com.tms.model.Service updateService(com.tms.model.Service service) {
        try {
            return serviceRepository.saveAndFlush(service);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void deleteService(int id) {
        serviceRepository.deleteService(id);
    }
}