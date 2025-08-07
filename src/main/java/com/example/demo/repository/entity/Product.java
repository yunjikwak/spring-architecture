package com.example.demo.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@ToString
public class Product extends AggregateRoot {
    @Setter
    private Long id;
    private final Long vendorId;
    private final String name;
    private final String imageUrl;
    private final Category category;
    private final List<ProductOption> options;

    public Product(Long venderId, String name, String imageUrl, Category category, List<ProductOption> options) {
        this.vendorId = venderId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options = options;
    }

    public boolean hasStock() {
        return options.stream().anyMatch(option -> option.getStock() > 0);
    }

    public boolean isAvailable(Long optionId, int quantity) {
        return getOptionById(optionId)
                .map(option -> option.getStock() >= quantity)
                .orElse(false);
    }

    public void decreaseStock(Long optionId, int quantity) {
        getOptionById(optionId)
                .ifPresent(option -> {
                    if (option.getStock() < quantity) {
                        throw new IllegalArgumentException("재고가 부족합니다. 상품명 : " + name);
                    }
                    option.decreaseStock(quantity);
                });
    }

    public Optional<ProductOption> getOptionById(Long optionId) {
        return options.stream()
                .filter(o -> o.getId().equals(optionId))
                .findFirst();
    }
}
