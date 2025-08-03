package com.example.demo.controller.page;

import com.example.demo.controller.page.dto.AddProductToCartRequestDto;
import com.example.demo.service.usecases.IManageCartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartPageController {
    private final IManageCartUseCase cartService;
    private static final Long MOCK_CUSTOMER_ID = 1L;

    @GetMapping
    public String displayCart(Model model) {
        cartService.getCart(MOCK_CUSTOMER_ID)
                .ifPresent(cart -> model.addAttribute("cart", cart));
        return "cart/list";
    }

    @PostMapping("/add")
    public String addProductToCart(@ModelAttribute AddProductToCartRequestDto request) {
        cartService.addProductToCart(
                MOCK_CUSTOMER_ID,
                request.getProductId(),
                request.getOptionId(),
                request.getQuantity()
        );
        return "redirect:/cart";
    }
}
