package com.project.shopapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(Integer orderId);
}
