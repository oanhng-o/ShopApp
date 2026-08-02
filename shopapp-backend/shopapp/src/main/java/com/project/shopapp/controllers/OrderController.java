package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ApiResponse;
import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.services.impl.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(ApiResponse.success("Create order successfully", order));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByUserId(@PathVariable("user_id") Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId.intValue());
        return ResponseEntity.ok(ApiResponse.success("Orders of ID:" + userId, orders));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable("id") int id, @Valid @RequestBody OrderDTO OrderDTO) {
        Order order = orderService.updateOrder(id, OrderDTO);
        return ResponseEntity.ok(ApiResponse.success("Update order with ID:" + id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> deleteOrder(@PathVariable("id") int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Delete order with ID:" + id, null));
    }
}
