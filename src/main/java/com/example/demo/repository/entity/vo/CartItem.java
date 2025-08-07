package com.example.demo.repository.entity.vo;

import com.example.demo.repository.entity.Product;
import com.example.demo.repository.entity.ProductOption;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CartItem {
    private final Long productId;
    private final String productName;
    private final Long optionId;
    private final String optionName;
    private final long price;
    private int quantity;

    // 이것도 어노테이션 생성자 생성을 생략 못하나? X -> 매개변수 타입 불일치(Prodcut, ProductOption), 도메인 객체 → 원시 값 변환 로직 필요
    // product와 option으로 CartItem 생성
    public CartItem(Product product, ProductOption option, int quantity) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.optionId = option.getId();
        this.optionName = option.getName();
        this.price = option.getPrice();
        this.quantity = quantity;
    }

    // 비즈니스 로직들
    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public long calculatePrice() {
        return price * quantity;
    }
}
