package com.example.demo.controller.api;

import com.example.demo.repository.entity.Product;
import com.example.demo.service.usecases.IDisplayProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final IDisplayProductUseCase productService;

    @GetMapping("")
    public String listProducts(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("prodcuts", productService.getProducts(keyword));
        model.addAttribute("keyword", keyword);
        return "product/list";
    }

    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "product/detail";
    }
}
