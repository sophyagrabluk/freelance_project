package com.tms.controller;

import com.tms.model.Service;
import com.tms.model.response.ServiceResponse;
import com.tms.service.ServiceService;
import com.tms.utils.SectionType;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceResponseById(@PathVariable int id) {
        return new ResponseEntity<>(serviceService.getServiceResponseById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAllServicesResponse() {
        return new ResponseEntity<>(serviceService.getAllServicesResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Give all services provided by one user")
    @GetMapping("/fromUser/{userId}")
    public ResponseEntity<List<ServiceResponse>> findServiceResponseByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(serviceService.findServiceResponseByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Give all services from one section")
    @GetMapping("/section/{section}")
    public ResponseEntity<List<ServiceResponse>> findServiceResponseBySection(@PathVariable SectionType section) {
        return new ResponseEntity<>(serviceService.findServiceResponseBySection(section), HttpStatus.OK);
    }

    @Operation(summary = "Give all available services according to rating")
    @GetMapping("/sortByRating")
    public ResponseEntity<List<ServiceResponse>> getAllServicesFromHighestRating() {
        return new ResponseEntity<>(serviceService.getAllServicesFromHighestRating(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service, BindingResult bd) {
        serviceService.createService(service, bd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateService(@RequestBody Service service) {
        serviceService.updateService(service);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Service> deleteService(@RequestParam int id) {
        serviceService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Provide all information for one service in database (including deleted)")
    @GetMapping("/admin/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable int id) {
        return new ResponseEntity<>(serviceService.getServiceById(id), HttpStatus.OK);
    }

    @Operation(summary = "Provide all services' information in database (including deleted)")
    @GetMapping("/admin")
    public ResponseEntity<List<Service>> getAllServices() {
        return new ResponseEntity<>(serviceService.getAllServices(), HttpStatus.OK);
    }

    @Operation(summary = "Give all services' information provided by one user(including deleted)")
    @GetMapping("/admin/fromUser/{userId}")
    public ResponseEntity<List<Service>> findServiceByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(serviceService.findServiceByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Give all services' information from one section(including deleted)")
    @GetMapping("/admin/section/{section}")
    public ResponseEntity<List<Service>> findServiceBySection(@PathVariable SectionType section) {
        return new ResponseEntity<>(serviceService.findServiceBySection(section), HttpStatus.OK);
    }

    @DeleteMapping("/admin")
    public ResponseEntity<Service> deleteServiceByAdmin(@RequestParam int id) {
        serviceService.deleteServiceByAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}