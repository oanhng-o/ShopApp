package com.project.shopapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

}
