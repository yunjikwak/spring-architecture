package com.example.demo.controller.api.dto;

import com.example.demo.repository.entity.Category;
import com.example.demo.repository.entity.Product;
import com.example.demo.repository.entity.ProductOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOptionResponseDto {
    private final Long id;
    private final String name;
    private final long price;
    private final int stock;

    public static ProductOptionResponseDto from(ProductOption entity) {
        return new ProductOptionResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock()
        );
    }

}
