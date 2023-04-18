package com.tms.controller;

import com.tms.domain.Service;
import com.tms.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Service getServiceById(@PathVariable int id) {
        return serviceService.getServiceById(id);
    }

    @GetMapping
    public ArrayList<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/forUser/{userId}")
    public ArrayList<Service> getServiceFromOneUser(@PathVariable int userId) {
        return serviceService.getServiceFromOneUser(userId);
    }

    @PostMapping
    public String createService(@RequestBody @Valid Service service, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return "unsuccessfully";
        }
        boolean result = serviceService.createService(service);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @PutMapping
    public String updateService(@RequestBody @Valid Service service, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("Oops, these are binding errors" + o);
            }
            return "unsuccessfully";
        }
        boolean result = serviceService.updateService(service);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @DeleteMapping
    public String deleteService(@RequestParam int id) {
        if (serviceService.deleteService(id)) {
            return "successfully";
        }
        return "unsuccessfully";
    }
}
