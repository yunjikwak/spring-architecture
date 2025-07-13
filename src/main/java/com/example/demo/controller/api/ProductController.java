package com.example.demo.controller.api;

import com.example.demo.controller.api.dto.ProductResponseDto;
import com.example.demo.repository.entity.Product;
import com.example.demo.service.usecases.IDisplayProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IDisplayProductUseCase productService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<ProductResponseDto>> listProducts() {
        List<Product> retrieved = productService.getProducts(null);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(retrieved.stream().map(ProductResponseDto::from).toList());
    }

    @GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<ProductResponseDto> productDetail(@PathVariable Long productId) {
        Product retrieved = productService.getProductById(productId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProductResponseDto.from(retrieved));
    }
}
