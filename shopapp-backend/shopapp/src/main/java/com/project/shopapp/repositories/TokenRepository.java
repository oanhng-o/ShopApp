package com.project.shopapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

}
