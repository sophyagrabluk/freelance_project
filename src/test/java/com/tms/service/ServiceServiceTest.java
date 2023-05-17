package com.tms.service;

import com.tms.mapper.ServiceToServiceResponseMapper;
import com.tms.model.Service;
import com.tms.model.response.ServiceResponse;
import com.tms.repository.ServiceRepository;
import com.tms.security.CheckingAuthorization;
import com.tms.utils.SectionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceTest {

    @InjectMocks
    private ServiceService serviceService;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceToServiceResponseMapper serviceToServiceResponseMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private CheckingAuthorization checkingAuthorization;

    private int id;

    private Service service;

    private ServiceResponse serviceResponse;

    private final List<Service> services = new ArrayList<>();

    private final List<ServiceResponse> serviceResponses = new ArrayList<>();

    @BeforeEach
    public void init() {
        id = 1;
        service = new Service();
        service.setId(1);
        service.setName("NameTest");
        service.setSection(SectionType.DESIGN);
        service.setDescription("DescriptionTest");
        service.setRating(5.0);
        service.setUserId(1);
        service.setUserLogin("UserLoginTest");
        service.setDeleted(false);
        services.add(service);
        serviceResponse = new ServiceResponse();
        serviceResponses.add(serviceResponse);
    }

    @Test
    public  void getServiceByIdTest() {
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceToServiceResponseMapper.serviceToResponse(service)).thenReturn(serviceResponse);
        ServiceResponse returned = serviceService.getServiceResponseById(id);
        verify(serviceRepository).findById(id);
        verify(serviceToServiceResponseMapper).serviceToResponse(service);
        assertEquals(serviceResponse, returned);
    }

    @Test
    public void getAllServicesTest() {
        when(serviceRepository.findAll()).thenReturn(services);
        when(serviceToServiceResponseMapper.serviceToResponse(service)).thenReturn(serviceResponse);
        assertEquals(serviceResponses, serviceService.getAllServicesResponse());
    }

    @Test
    public void findServiceResponseByUserIdTest(){
        when(serviceRepository.findServiceByUserId(id)).thenReturn(services);
        when(serviceToServiceResponseMapper.serviceToResponse(service)).thenReturn(serviceResponse);
        assertEquals(serviceResponses, serviceService.findServiceResponseByUserId(id));
    }

    @Test
    public void findServiceResponseBySectionTest() {
        when(serviceRepository.findServicesBySectionOrderByRatingDesc(service.getSection())).thenReturn(services);
        when(serviceToServiceResponseMapper.serviceToResponse(service)).thenReturn(serviceResponse);
        assertEquals(serviceResponses, serviceService.findServiceResponseBySection(service.getSection()));
    }

    @Test
    public void getAllServicesFromHighestRatingTest() {
        when(serviceRepository.findServicesByOrderByRatingDesc()).thenReturn(services);
        when(serviceToServiceResponseMapper.serviceToResponse(service)).thenReturn(serviceResponse);
        assertEquals(serviceResponses, serviceService.getAllServicesFromHighestRating());
    }

    @Test
    public void createServiceTest() {
        serviceService.createService(service, bindingResult);
        verify(serviceRepository).save(service);
    }

    @Test
    public void updateServiceTest() {
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(checkingAuthorization.check(service.getUserLogin())).thenReturn(true);
        serviceService.updateService(service);
        verify(serviceRepository).saveAndFlush(service);
    }

    @Test
    public void deleteServiceTest() {
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(checkingAuthorization.check(service.getUserLogin())).thenReturn(true);
        serviceService.deleteService(id);
        verify(serviceRepository).deleteService(id);
    }
}