package com.tms;

import com.tms.model.Order;
import com.tms.repository.OrderRepository;
import com.tms.security.CheckingAuthorization;
import com.tms.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CheckingAuthorization checkingAuthorization;

    private int id;

    private Order order;

    private String userLogin;

    private final List<Order> orders = new ArrayList<>();

    private final Timestamp time = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    public void init() {
        id = 1;
        userLogin = "userLoginTest";
        order = new Order();
        order.setId(1);
        order.setCreated(time);
        order.setServiceId(1);
        order.setUserId(1);
        order.setStatus("IN PROGRESS");
        orders.add(order);
    }

    @Test
    public void getOrderByIdTest() {
        when(orderRepository.getUserLogin(id)).thenReturn(userLogin);
        when(checkingAuthorization.check(userLogin)).thenReturn(true);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Order returned = orderService.getOrderById(id);
        verify(orderRepository).getUserLogin(id);
        assertEquals(order, returned);
    }

    @Test
    public void createOrderTest() {
        order.setCreated(time);
        order.setStatus(order.getStatus());
        orderService.createOrder(order);
        verify(orderRepository).save(order);
    }

    @Test
    public void finishOrderTest() {
        when(orderRepository.getUserLogin(id)).thenReturn(userLogin);
        when(checkingAuthorization.check(userLogin)).thenReturn(true);
        orderService.finishOrder(id);
        verify(orderRepository).finishOrder(id);
    }

    @Test
    public void getAllActiveOrdersFromOneUserTest() {
        when(orderRepository.getUserLogin(id)).thenReturn(userLogin);
        when(checkingAuthorization.check(userLogin)).thenReturn(true);
        when(orderRepository.findAllByUserId(id)).thenReturn(orders);
        assertEquals(orders, orderService.getAllActiveOrdersFromOneUser(id));
    }

    @Test
    public void getAllFinishedOrdersFromOneUserTest() {
        order.setStatus("FINISHED");
        when(orderRepository.getUserLogin(id)).thenReturn(userLogin);
        when(checkingAuthorization.check(userLogin)).thenReturn(true);
        when(orderRepository.findAllByUserId(id)).thenReturn(orders);
        assertEquals(orders, orderService.getAllFinishedOrdersFromOneUser(id));
    }
}