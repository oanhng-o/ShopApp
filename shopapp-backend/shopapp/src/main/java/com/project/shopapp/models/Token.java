package com.project.shopapp.models;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String token;

    @Column(name = "token_type", nullable = false, length = 50)
    private String tokenType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
