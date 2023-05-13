package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.ForbiddenException;
import com.tms.exception.NotFoundExc;
import com.tms.mapper.ServiceToServiceResponseMapper;
import com.tms.model.response.ServiceResponse;
import com.tms.repository.ServiceRepository;
import com.tms.security.CheckingAuthorization;
import com.tms.utils.SectionType;
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

    private final ServiceRepository serviceRepository;
    private final ServiceToServiceResponseMapper serviceToServiceResponseMapper;
    private final CheckingAuthorization checkingAuthorization;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository, ServiceToServiceResponseMapper serviceToServiceResponseMapper, CheckingAuthorization checkingAuthorization) {
        this.serviceRepository = serviceRepository;
        this.serviceToServiceResponseMapper = serviceToServiceResponseMapper;
        this.checkingAuthorization = checkingAuthorization;
    }

    public ServiceResponse getServiceById(int id) {
        Optional<com.tms.model.Service> selectedService = serviceRepository.findById(id);
        if (selectedService.isPresent() && !selectedService.get().isDeleted()) {
            return serviceToServiceResponseMapper.serviceToResponse(selectedService.get());
        } else {
            throw new NotFoundExc("There is no such service");
        }
    }

    public List<ServiceResponse> getAllServices() {
        List<ServiceResponse> services = serviceRepository.findAll().stream()
                .filter(service -> !service.isDeleted())
                .map(serviceToServiceResponseMapper::serviceToResponse)
                .collect(Collectors.toList());
        if (!services.isEmpty()) {
            return services;
        } else {
            throw new NotFoundExc("There are no services");
        }
    }

    public List<ServiceResponse> findServiceByUserId(int userId) {
        List<ServiceResponse> services = serviceRepository.findServiceByUserId(userId).stream()
                .filter(service -> !service.isDeleted())
                .map(serviceToServiceResponseMapper::serviceToResponse)
                .collect(Collectors.toList());
        if (!services.isEmpty()) {
            return services;
        } else {
            throw new NotFoundExc("There are no services from this user");
        }
    }

    public List<ServiceResponse> findServiceBySection(SectionType section) {
        List<ServiceResponse> services = serviceRepository.findServicesBySectionOrderByRatingDesc(section).stream()
                .filter(service -> !service.isDeleted())
                .map(serviceToServiceResponseMapper::serviceToResponse)
                .collect(Collectors.toList());
        if (!services.isEmpty()) {
            return services;
        } else {
            throw new NotFoundExc("There are no services from this section");
        }
    }

    public List<ServiceResponse> getAllServicesFromHighestRating() {
        List<ServiceResponse> services = serviceRepository.findServicesByOrderByRatingDesc().stream()
                .filter(service -> !service.isDeleted())
                .map(serviceToServiceResponseMapper::serviceToResponse)
                .collect(Collectors.toList());
        if (!services.isEmpty()) {
            return services;
        } else {
            throw new NotFoundExc("There are no services");
        }
    }

    public void createService(@Valid com.tms.model.Service service, BindingResult bindingResult) {
        serviceRepository.save(service);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Check your info and try again");
        }
    }

    public void updateService(com.tms.model.Service service) {
        com.tms.model.Service gotService = serviceRepository.findById(service.getId()).get();
        if (checkingAuthorization.check(gotService.getUserLogin())) {
            serviceRepository.saveAndFlush(service);
        } else {
            throw new ForbiddenException("You can't update not your own service");
        }
    }

    @Transactional
    public void deleteService(int id) {
        if (checkingAuthorization.check(getUserLogin(id))) {
            serviceRepository.deleteService(id);
        } else {
            throw new ForbiddenException("You can't delete not your own service");
        }
    }

    private String getUserLogin(int id) {
        return serviceRepository.findById(id).get().getUserLogin();
    }
}