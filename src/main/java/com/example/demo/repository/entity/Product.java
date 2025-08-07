package com.example.demo.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@ToString
public class Product extends AggregateRoot { // 도메인 엔티티
    @Setter
    private Long id;
    private final Long vendorId;
    private final String name;
    private final String imageUrl;
    private final Category category;
    private final List<ProductOption> options;

    // 왜 굳이 @RequiredArgsConstructor을 사용X? -> 사용 가능 !
    public Product(Long vendorId, String name, String imageUrl, Category category, List<ProductOption> options) {
        this.vendorId = vendorId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options = options;
    }

    // 최소 하나 이상의 옵션에 재고가 있는지 확인
    // stream() -> anyMatch(): 조건 만족 요소 하나라도 있으면 true
    public boolean hasStock() {
        return options.stream().anyMatch(option -> option.getStock() > 0);
    }

    // 특정 옵션에 요청 수량만큼 재고 존재하는지 확인
    // stream() -> filter() -> findFirst() -> map() -> orElse()
    public boolean isAvailable(Long optionId, int quantity) {
        return getOptionById(optionId)
                .map(option -> option.getStock() >= quantity)
                .orElse(false);
    }

    // 옵션 ID로 옵션 조회
    public Optional<ProductOption> getOptionById(Long optionId) {
        return options.stream()
                .filter(o -> o.getId().equals(optionId))
                .findFirst();
    }

    // 재고 감소
    // getOptionById() -> ifPresent() -> decreaseStock()
    public void decreaseStock(Long optionId, int quantity) {
        getOptionById(optionId)
                .ifPresent(option -> {
                    if (option.getStock() < quantity) {
                        throw new IllegalArgumentException("재고가 부족합니다. 상품명: " + name);
                    }
                    option.decreaseStock(quantity);
                });
    }
}
