package com.project.shopapp.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.project.shopapp.dtos.products.ProductImageDTO;
import com.project.shopapp.dtos.products.ProductResponseDTO;
import com.project.shopapp.exception.InvalidArgumentException;
import com.project.shopapp.exception.ResourceExistsException;
import com.project.shopapp.models.ProductImage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.shopapp.dtos.ApiResponse;
import com.project.shopapp.dtos.products.ProductDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.services.impl.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "")
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @Validated @RequestBody ProductDTO productDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();

            if (!fieldErrors.isEmpty()) {
                FieldError fieldError = fieldErrors.get(0);
                throw new InvalidArgumentException(fieldError.getField() + " "
                        + fieldError.getDefaultMessage());
            }
        }
        if (productService.existsByName(productDTO.getName()))
            throw new ResourceExistsException("Product already exists.");
        Product product = productService.createProduct(productDTO);
        return ResponseEntity.ok(ApiResponse.success("Product created successfully", product));
    }

    private ProductImageDTO storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path destinationFile = Paths.get(uploadPath.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        return ProductImageDTO.builder()
                .imageUrl(destinationFile.toString())
                .build();
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        // page bat dau tu 0
        PageRequest pageRequest = PageRequest.of(page - 1, limit,
                Sort.by("createdAt").descending());
        List<ProductResponseDTO> products = productService.getAllProducts(pageRequest);
        return ResponseEntity.ok(ApiResponse
                .success(String.format("Products retrieved successfully, page: %d, limit: %d", page, limit),
                        products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(String.format("Product details for ID: %d", id), product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable("id") int id,
            @RequestBody @Validated ProductDTO productDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();

            if (!fieldErrors.isEmpty()) {
                FieldError fieldError = fieldErrors.get(0);
                throw new InvalidArgumentException(fieldError.getField() + " "
                        + fieldError.getDefaultMessage());
            }
        }

        Product updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(ApiResponse.success(String.format("Product updated (ID: %d)", id), updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(String.format("Product deleted (ID: %d)", id), null));
    }

    @PostMapping(value = "/uploads/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> uploadImages(
            @PathVariable("productId") Integer productId,
            @ModelAttribute List<MultipartFile> files) throws IOException {
        Product existingProduct = productService.getProductById(productId);
        if (files == null) {
            files = new ArrayList<MultipartFile>();
        }
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue;
            }
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB limit
                throw new InvalidArgumentException("File size exceeds the maximum limit of 10MB");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new InvalidArgumentException("Invalid file type. Only image files are allowed.");
            }
            ProductImage productImage = productService.createProductImage(productId, storeFile(file));
            productImages.add(productImage);
        }
        return ResponseEntity.ok(ApiResponse.success("Upload images successfully", productImages));
    }
}
