package com.tms.service;

import com.tms.model.Order;
import com.tms.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {

    OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(int id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            return order.orElse(null);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    public Order createOrder(Order order) {
        try {
            order.setCreated(new Timestamp(System.currentTimeMillis()));
            order.setStatus("IN PROGRESS");
            return orderRepository.save(order);
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void finishOrder(int id) {
        orderRepository.finishOrder(id);
    }

    public ArrayList<Order> getAllOrdersFromOneUser (Integer userId) {
        return orderRepository.findAllByUserId(userId).orElse(new ArrayList<>());
    }
}
