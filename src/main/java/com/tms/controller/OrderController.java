package com.tms.controller;

import com.tms.model.Order;
import com.tms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {

    OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById (@PathVariable int id){
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/fromUser/{userId}")
    public ResponseEntity<ArrayList<Order>> getAllOrdersFromOneUser (@PathVariable int userId) {
        ArrayList<Order> ordersFromOneUsers = orderService.getAllOrdersFromOneUser(userId);
        if (ordersFromOneUsers == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersFromOneUsers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder (@RequestBody Order order){
        Order orderResult = orderService.createOrder(order);
        if (orderResult == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Order> finishOrder (@RequestParam Integer id){
        try {
            orderService.finishOrder(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
