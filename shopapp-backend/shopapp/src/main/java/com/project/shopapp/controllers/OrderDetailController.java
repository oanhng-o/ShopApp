package com.project.shopapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dtos.OrderDetailDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO,
                                                BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            return ResponseEntity.ok("Create order detail successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Order detail with ID:" + id);
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@PathVariable("order_id") Long orderId) {
        return ResponseEntity.ok("Order details with order ID:" + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Update order detail with ID:" + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Delete order detail with ID:" + id);
    }
}
