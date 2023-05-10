package com.tms.service;

import com.tms.exception.NotFoundException;
import com.tms.model.Order;
import com.tms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.orElse(null);
        } else {
            throw new NotFoundException("There is no such order");
        }
    }

    public Order createOrder(Order order) {
            order.setCreated(new Timestamp(System.currentTimeMillis()));
            order.setStatus("IN PROGRESS");
            return orderRepository.save(order);
    }

    public void finishOrder(int id) {
        orderRepository.finishOrder(id);
    }

    public List<Order> getAllActiveOrdersFromOneUser(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (!orders.isEmpty()) {
            return orders.stream().filter(order -> order.getStatus().contains("IN PROGRESS")).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no active orders");
        }
    }

    public List<Order> getAllFinishedOrdersFromOneUser(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (!orders.isEmpty()) {
            return orders.stream().filter(order -> order.getStatus().contains("FINISHED")).collect(Collectors.toList());
        } else {
            throw new NotFoundException("There are no finished orders");
        }
    }
}
