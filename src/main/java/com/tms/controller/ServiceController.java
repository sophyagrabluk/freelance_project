package com.tms.controller;

import com.tms.domain.Service;
import com.tms.domain.User;
import com.tms.service.ServiceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/service")
public class ServiceController {

    ServiceService serviceService;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/{id}")
    public String getServiceById(@PathVariable int id, Model model) {
        Service service = serviceService.getServiceById(id);
        model.addAttribute("service", service);
        return "singleService";
    }

    @GetMapping
    public String getAllServices(Model model) {
        ArrayList<Service> services = serviceService.getAllServices();
        model.addAttribute("user", services);
        return "singleService";
    }

    @PostMapping
    public String createService(@ModelAttribute @Valid Service service, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            logger.warn("Oops, error with validation");
            return "unsuccessfully";
        }
        boolean result = serviceService.createService(service);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @PutMapping
    public String updateService(@ModelAttribute @Valid Service service, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            logger.warn("Oops, error with validation");
            return "unsuccessfully";
        }
        boolean result = serviceService.updateService(service);
        if (result) {
            return "successfully";
        }
        return "unsuccessfully";
    }

    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable int id) {
        if (serviceService.deleteService(id)) {
            return "successfully";
        }
        return "unsuccessfully";
    }
}
