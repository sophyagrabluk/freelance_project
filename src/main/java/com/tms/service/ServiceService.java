package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.NotFoundException;
import com.tms.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public com.tms.model.Service getServiceById(int id) {
        Optional<com.tms.model.Service> service = serviceRepository.findById(id);
        if (service.isPresent() && !service.get().isDeleted()) {
            return service.orElse(null);
        } else {
            throw new NotFoundException("There is no such service");
        }
    }

    public List<com.tms.model.Service> getAllServices() {
        List<com.tms.model.Service> services = serviceRepository.findAll();
        if (!services.isEmpty()) {
            return services.stream().filter(service -> !service.isDeleted()).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no services");
        }
    }

    public List<com.tms.model.Service> findServiceByUserId(int userId) {
        List<com.tms.model.Service> services = serviceRepository.findServiceByUserId(userId);
        if (!services.isEmpty()){
            return services.stream().filter(service -> !service.isDeleted()).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no services from this user");
        }
    }

    public List<com.tms.model.Service> findServiceBySection(String section) {
        List<com.tms.model.Service> services = serviceRepository.findServicesBySectionOrderByRatingDesc(section);
        if (!services.isEmpty()){
            return services.stream().filter(service -> !service.isDeleted()).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no services from this section");
        }
    }

    public List<com.tms.model.Service> getAllServicesFromHighestRating() {
        List<com.tms.model.Service> services = serviceRepository.findServicesByOrderByRatingDesc();
        if (!services.isEmpty()){
            return services.stream().filter(service -> !service.isDeleted()).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no services");
        }
    }

    public com.tms.model.Service createService(@Valid com.tms.model.Service service, BindingResult bindingResult) {
        com.tms.model.Service newService = serviceRepository.save(service);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Check your info and try again");
        } else {
            return newService;
        }
    }

    public com.tms.model.Service updateService(@Valid com.tms.model.Service service, BindingResult bindingResult) {
        com.tms.model.Service updateService = serviceRepository.saveAndFlush(service);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Check your info and try again");
        } else {
            return updateService;
        }
    }

    @Transactional
    public void deleteService(int id) {
        serviceRepository.deleteService(id);
    }
}