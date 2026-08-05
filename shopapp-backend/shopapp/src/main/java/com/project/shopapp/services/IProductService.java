package com.project.shopapp.services;

import com.project.shopapp.dtos.products.ProductDTO;
import com.project.shopapp.dtos.products.ProductImageDTO;
import com.project.shopapp.dtos.products.ProductResponseDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO product);
    List<ProductResponseDTO> getAllProducts(PageRequest pageRequest);
    Product getProductById(Integer id);
    Product updateProduct(Integer id, ProductDTO product);
    void deleteProduct(Integer id);
    boolean existsByName(String name);
    ProductImage createProductImage(Integer productId, ProductImageDTO productImageDTO);
}
