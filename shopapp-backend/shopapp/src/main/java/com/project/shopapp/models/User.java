package com.project.shopapp.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(length = 200)
    private String address;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
