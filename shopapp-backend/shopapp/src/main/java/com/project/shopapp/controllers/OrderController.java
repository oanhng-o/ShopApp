package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO OrderDTO,
                                        BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            return ResponseEntity.ok("Create order successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok("Orders of ID:" + userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Update order with ID:" + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Delete order with ID:" + id);
    }
}
