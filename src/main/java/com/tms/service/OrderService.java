package com.tms.service;

import com.tms.exception.NotFoundExc;
import com.tms.model.Order;
import com.tms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundExc("There is no such order"));
    }

    public void createOrder(Order order) {
        order.setCreated(new Timestamp(System.currentTimeMillis()));
        order.setStatus("IN PROGRESS");
        orderRepository.save(order);
    }

    public void finishOrder(int id) {
        orderRepository.finishOrder(id);
    }

    public List<Order> getAllActiveOrdersFromOneUser(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (!orders.isEmpty()) {
            return orders.stream().filter(order -> order.getStatus().contains("IN PROGRESS")).collect(Collectors.toList());
        } else {
            throw new NotFoundExc("There are no active orders");
        }
    }

    public List<Order> getAllFinishedOrdersFromOneUser(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (!orders.isEmpty()) {
            return orders.stream().filter(order -> order.getStatus().contains("FINISHED")).collect(Collectors.toList());
        } else {
            throw new NotFoundExc("There are no active orders");
        }
    }
}