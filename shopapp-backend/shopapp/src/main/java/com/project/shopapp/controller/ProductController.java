package com.project.shopapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.shopapp.dtos.ProductDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
        @Valid @ModelAttribute ProductDTO productDTO,
        BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }
        List<MultipartFile> files = productDTO.getFiles();
        if (files == null) {
            files = new ArrayList<MultipartFile>();
        }
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue; 
            }
            if(file.getSize() > 10 * 1024 * 1024) { // 10MB limit
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("File size exceeds the maximum limit of 10MB");
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid file type. Only image files are allowed.");
            }
            String fileName = storeFile(file);
        }
       
        return ResponseEntity.status(HttpStatus.OK).body("Product created: " + productDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating product: " + e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadPath = Paths.get("uploads");
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path destinationFile = Paths.get(uploadPath.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
    
    @GetMapping("")
    public ResponseEntity<String> getProducts(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("List of products (Page: %d, Limit: %d)", page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Product details for ID: %d", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Product deleted (ID: %d)", id));
    }
}
