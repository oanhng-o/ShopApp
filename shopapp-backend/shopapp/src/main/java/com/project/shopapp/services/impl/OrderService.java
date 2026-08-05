package com.project.shopapp.services.impl;

import java.util.List;

import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.services.IOrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        int userId = orderDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found (ID: %d).", userId)));
        return Order.fromDTO(orderDTO, user);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Order not found (ID: %d)", id)));
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order updateOrder(int id, OrderDTO orderDTO) {
        Order existingOrder = getOrderById(id);

        int userId = orderDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found (ID: %d).", userId)));

        existingOrder.setUser(user);
        existingOrder.setFullName(orderDTO.getFullName());
        existingOrder.setEmail(orderDTO.getEmail());
        existingOrder.setPhoneNumber(orderDTO.getPhoneNumber());
        existingOrder.setAddress(orderDTO.getAddress());
        existingOrder.setNote(orderDTO.getNote());
        existingOrder.setTotalMoney(orderDTO.getTotalMoney());
        existingOrder.setShippingMethod(orderDTO.getShippingMethod());
        existingOrder.setShippingAddress(orderDTO.getShippingAddress());
        existingOrder.setShippingDate(orderDTO.getShippingDate());
        existingOrder.setTrackingNumber(orderDTO.getTrackingNumber());
        existingOrder.setPaymentMethod(orderDTO.getPaymentMethod());

        return orderRepository.save(existingOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
