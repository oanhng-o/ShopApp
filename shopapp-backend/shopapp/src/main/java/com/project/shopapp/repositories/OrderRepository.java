package com.project.shopapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
}
