package com.tms.service;

import com.tms.exception.BadRequestException;
import com.tms.exception.NotFoundExc;
import com.tms.mapper.ServiceToServiceResponseMapper;
import com.tms.model.response.ServiceResponse;
import com.tms.repository.ServiceRepository;
import com.tms.utils.SectionType;
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
    ServiceToServiceResponseMapper serviceToServiceResponseMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceToServiceResponseMapper serviceToServiceResponseMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceToServiceResponseMapper = serviceToServiceResponseMapper;
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
                .map(service -> serviceToServiceResponseMapper.serviceToResponse(service))
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
                .map(service -> serviceToServiceResponseMapper.serviceToResponse(service))
                .collect(Collectors.toList());
        if (!services.isEmpty()){
            return services;
        } else {
            throw new NotFoundExc("There are no services from this user");
        }
    }

    public List<ServiceResponse> findServiceBySection(SectionType section) {
        List<ServiceResponse> services = serviceRepository.findServicesBySectionOrderByRatingDesc(section).stream()
                .filter(service -> !service.isDeleted())
                .map(service -> serviceToServiceResponseMapper.serviceToResponse(service))
                .collect(Collectors.toList());
        if (!services.isEmpty()){
            return services;
        } else {
            throw new NotFoundExc("There are no services from this section");
        }
    }

    public List<ServiceResponse> getAllServicesFromHighestRating() {
        List<ServiceResponse> services = serviceRepository.findServicesByOrderByRatingDesc().stream()
                .filter(service -> !service.isDeleted())
                .map(service -> serviceToServiceResponseMapper.serviceToResponse(service))
                .collect(Collectors.toList());;
        if (!services.isEmpty()){
            return services;
        } else {
            throw new NotFoundExc("There are no services");
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