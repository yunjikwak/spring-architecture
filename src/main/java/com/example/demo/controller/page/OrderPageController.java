package com.example.demo.controller.page;

import com.example.demo.controller.page.dto.ProcessOrderRequestDto;
import com.example.demo.repository.entity.Cart;
import com.example.demo.repository.entity.Order;
import com.example.demo.repository.entity.vo.PaymentMethod;
import com.example.demo.service.usecases.IManageCartUseCase;
import com.example.demo.service.usecases.IPurchaseOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderPageController {
    private final IPurchaseOrderUseCase orderService;
    private final IManageCartUseCase cartService;
    private static final Long MOCK_CUSTOMER_ID = 1L;

    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        Cart cart = cartService.getCart(MOCK_CUSTOMER_ID)
                .orElseThrow(() -> new IllegalStateException("장바구니가 비어있습니다."));
        model.addAttribute("cart", cart);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("orderRequest", new ProcessOrderRequestDto());
        return "orders/checkout";
    }

    @PostMapping("/process")
    public String processOrder(@ModelAttribute ProcessOrderRequestDto request, Model model) {
        try {
            Order order = orderService.process(MOCK_CUSTOMER_ID, request.toShippingInfo(), request.getPaymentMethod(), null);
            model.addAttribute("order", order);
            return "order/complete";
        } catch(IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "order/failure";
        }
    }

    @GetMapping("/{orderId}")
    public String orderStatus(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "order/status";
    }
}
