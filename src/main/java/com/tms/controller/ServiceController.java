package com.tms.controller;

import com.tms.model.Service;
import com.tms.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/service")
public class ServiceController {

    ServiceService serviceService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable int id) {
        Service service = serviceService.getServiceById(id);
        if (service == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Service>> getAllServices() {
        ArrayList<Service> allServices = serviceService.getAllServices();
        if (allServices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allServices, HttpStatus.OK);
    }

    @GetMapping("/forUser/{userId}")
    public ArrayList<Service> getServiceFromOneUser(@PathVariable int userId) {
        return serviceService.getServiceFromOneUser(userId);
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<ArrayList<Service>> getServicesFromOneSection(@PathVariable String section) {
        ArrayList<Service> allServicesFromOneSection = serviceService.getServicesFromOneSection(section);
        if (allServicesFromOneSection.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createService(@RequestBody @Valid Service service, BindingResult bindingResult) {
        boolean result = serviceService.createService(service);
        if (bindingResult.hasErrors() || !result) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateService(@RequestBody @Valid Service service, BindingResult bindingResult) {
        boolean result = serviceService.updateService(service);
        if (bindingResult.hasErrors() || !result) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Service> deleteService(@RequestParam int id) {
        serviceService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}