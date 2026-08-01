package com.project.shopapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer> {

}
