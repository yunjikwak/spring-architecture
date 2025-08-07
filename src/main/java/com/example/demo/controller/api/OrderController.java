package com.example.demo.controller.api;

import com.example.demo.controller.api.dto.OrderResponseDto;
import com.example.demo.repository.entity.Order;
import com.example.demo.service.usecases.IPurchaseOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IPurchaseOrderUseCase orderService;
    private static final Long MOCK_CUSTOMER_ID = 1L;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<OrderResponseDto>> orderStatus() {
        List<Order> retrieved = orderService.getOrders(MOCK_CUSTOMER_ID);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(retrieved.stream().map(OrderResponseDto::from).toList());
    }

    @GetMapping("/{orderId}")
    @ResponseBody
    public ResponseEntity<OrderResponseDto> orderStatus(@PathVariable Long orderId) {
        Order retrieved = orderService.getOrderById(orderId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OrderResponseDto.from(retrieved));
    }
}
