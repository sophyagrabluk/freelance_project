package com.tms.controller;

import com.tms.model.Order;
import com.tms.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById (@PathVariable int id){
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @Operation(summary = "Provide all active orders made by one user")
    @GetMapping("/myActiveOrders/{userId}")
    public ResponseEntity<List<Order>> getAllActiveOrdersFromOneUser (@PathVariable int userId) {
        return new ResponseEntity<>(orderService.getAllActiveOrdersFromOneUser(userId), HttpStatus.OK);
    }

    @Operation(summary = "Provide all finished orders made by one user")
    @GetMapping("/myFinishedOrders/{userId}")
    public ResponseEntity<List<Order>> getAllFinishedOrdersFromOneUser (@PathVariable int userId) {
        return new ResponseEntity<>(orderService.getAllFinishedOrdersFromOneUser(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder (@RequestBody Order order){
        orderService.createOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Order> finishOrder (@RequestParam int id){
            orderService.finishOrder(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}