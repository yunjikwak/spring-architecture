package com.example.demo.controller.page.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductToCartRequestDto {
    private Long productId;
    private Long optionId;
    private int quantity;
}
