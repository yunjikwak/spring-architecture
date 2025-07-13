package com.example.demo.repository.entity;

import com.example.demo.repository.entity.vo.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Cart extends AggregateRoot {
    @Setter
    private Long id;
    private final Long customerId;
    private final List<CartItem> items = new ArrayList<>();

    public Cart(Long customerId) {
        this.customerId = customerId;
    }

    public void addProduct(Product product, ProductOption option, int quantity) {
        if (option.getStock() < quantity) {
            throw new IllegalStateException("상품의 재고가 부족하여 장바구니에 담을 수 없습니다.");
        }
        // 이미 담겨있는 상품/옵션 조합인지 확인
        items.stream()
                .filter(item -> item.getProductId().equals(product.getId()) && item.getOptionId().equals(option.getId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.increaseQuantity(quantity), // 있으면 수량 증가
                        () -> items.add(new CartItem(product, option, quantity)) // 없으면 새로 추가
                );
    }

    public long calculateTotalPrice() {
        return items.stream().mapToLong(CartItem::calculatePrice).sum();
    }
}
