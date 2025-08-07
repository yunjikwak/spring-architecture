package com.example.demo.controller.page;

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
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductPageController {
    private final IDisplayProductUseCase productService;

    @GetMapping("")
    public String listProducts(@RequestParam(required = false) String keyword, Model model) {
     model.addAttribute("products",  productService.getProducts(keyword));
        return "product/list";
    }

    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "product/detail";
    }
}
