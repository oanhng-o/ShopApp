package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.products.ProductImageDTO;
import com.project.shopapp.dtos.products.ProductResponseDTO;
import com.project.shopapp.exception.InvalidArgumentException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.products.ProductDTO;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.IProductService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Integer categoryId = productDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        Product product = Product.builder()
                .price(productDTO.getPrice())
                .category(category)
                .description(productDTO.getDescription())
                .thumbnail(productDTO.getThumbnail())
                .name(productDTO.getName())
                .build();
        return productRepository.save(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findAll(pageRequest);
        List<Product> products = productPage.getContent();
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO productResponseDTO = ProductResponseDTO
                    .builder()
                    .categoryName(product.getCategory().getName())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
            productResponseDTOS.add(productResponseDTO);
        }
        return productResponseDTOS;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(String.format("Product not found (ID: %d).", id)));
    }

    @Override
    public Product updateProduct(Integer id, ProductDTO productDTO) {
        Product existingProduct = getProductById(id);

        Integer categoryId = productDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setThumbnail(productDTO.getThumbnail());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Integer productId, ProductImageDTO productImageDTO) {
        Product product = getProductById(productId);
        if (productImageRepository.findByProductId(productId).size() >= 5) {
            throw new InvalidArgumentException("Number of image must be equal or less than 5.");
        }
        ProductImage productImage = ProductImage.builder()
                .imageUrl(productImageDTO.getImageUrl())
                .product(product)
                .build();
        return productImageRepository.save(productImage);
    }
}
