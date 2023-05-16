package com.tms.service;

import com.tms.exception.ForbiddenException;
import com.tms.exception.NotFoundExc;
import com.tms.model.Order;
import com.tms.repository.OrderRepository;
import com.tms.security.CheckingAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CheckingAuthorization checkingAuthorization;

    @Autowired
    public OrderService(OrderRepository orderRepository, CheckingAuthorization checkingAuthorization) {
        this.orderRepository = orderRepository;
        this.checkingAuthorization = checkingAuthorization;
    }

    public Order getOrderById(int id) {
        if (checkingAuthorization.check(getUserLogin(id))) {
            return orderRepository.findById(id).orElseThrow(() -> new NotFoundExc("There is no such order"));
        } else {
            throw new ForbiddenException("You can't get this order");
        }
    }

    public void createOrder(Order order) {
        order.setCreated(new Timestamp(System.currentTimeMillis()));
        order.setStatus("IN PROGRESS");
        orderRepository.save(order);
    }

    public void finishOrder(int id) {
        if (checkingAuthorization.check(getUserLogin(id))) {
            orderRepository.finishOrder(id);
        } else {
            throw new ForbiddenException("You can't finish this order");
        }
    }

    public List<Order> getAllActiveOrdersFromOneUser(Integer userId) {
        if (checkingAuthorization.check(getUserLogin(userId))) {
            List<Order> orders = orderRepository.findAllByUserId(userId);
            if (!orders.isEmpty()) {
                return orders.stream().filter(order -> order.getStatus().contains("IN PROGRESS")).collect(Collectors.toList());
            } else {
                throw new NotFoundExc("There are no active orders");
            }
        } else {
            throw new ForbiddenException("You can get only Your own orders");
        }
    }

    public List<Order> getAllFinishedOrdersFromOneUser(Integer userId) {
        if (checkingAuthorization.check(getUserLogin(userId))) {
            List<Order> orders = orderRepository.findAllByUserId(userId);
            if (!orders.isEmpty()) {
                return orders.stream().filter(order -> order.getStatus().contains("FINISHED")).collect(Collectors.toList());
            } else {
                throw new NotFoundExc("There are no active orders");
            }
        } else {
            throw new ForbiddenException("You can get only Your own orders");
        }
    }

    private String getUserLogin(int id) {
        return orderRepository.getUserLogin(id);
    }
}