package com.example.demo.controller.api.dto;

import com.example.demo.repository.entity.Order;
import com.example.demo.repository.entity.vo.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponseDto {
    private final Long id;
    private final Long customerId;
    private final List<OrderItemResponseDto> orderItems;
    private final long originalPrice;
    private final long discountPrice;
    private final String recipient;
    private final String address;
    private final String phone;
    private final OrderStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime orderDate;

    public static OrderResponseDto from(Order entity) {
        return new OrderResponseDto(
                entity.getId(),
                entity.getCustomerId(),
                entity.getOrderItems().stream()
                        .map(OrderItemResponseDto::from)
                        .toList(),
                entity.getOriginalPrice(),
                entity.getDiscountPrice(),
                entity.getShippingInfo().getRecipient(),
                entity.getShippingInfo().getAddress(),
                entity.getShippingInfo().getPhone(),
                entity.getStatus(),
                entity.getOrderDate()
        );
    }
}
