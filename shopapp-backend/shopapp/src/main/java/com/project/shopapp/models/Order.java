package com.project.shopapp.models;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 350)
    private String name;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(length = 100)
    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    private String status;

    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "shipping_method", length = 100)
    private String shippingMethod;

    @Column(name = "shipping_address", length = 200)
    private String shippingAddress;

    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "payment_method", length = 100)
    private String paymentMethod;
}
