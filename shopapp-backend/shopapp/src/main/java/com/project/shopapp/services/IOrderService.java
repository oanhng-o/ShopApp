package com.project.shopapp.services;

import java.util.List;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO);
    Order getOrderById(int id);
    List<Order> getOrdersByUserId(int userId);
    Order updateOrder(int id, OrderDTO orderDTO);
    void deleteOrder(int id);
}
