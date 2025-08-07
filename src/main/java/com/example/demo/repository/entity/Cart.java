package com.example.demo.repository.entity;

import com.example.demo.repository.entity.vo.CartItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    // 비즈니스 로직
    public void addProduct(Product product, ProductOption option, int quantity) {
        if (option.getStock() < quantity) {
            throw new IllegalStateException("상품의 재고가 부족하여 장바구니에 담을 수 없습니다.");
        }
        items.stream()// List -> stream
                .filter(item -> item.getProductId().equals(product.getId()) && item.getOptionId().equals(option.getId())) // 조건 필터링
                .findFirst() // 처음 발견 한 요소 Optional로 반환
                .ifPresentOrElse( // Optional 처리
                        item->item.increaseQuantity(quantity), // 값이 있을 때, Consumer<T> 람다
                        () -> items.add(new CartItem(product, option, quantity)) // 값이 없을 때, Runnable 람다
                );
    }

    public long calculateTotalPrice() {
        return items.stream() // ex) [CartItem1, CartItem2, CartItem3]
                .mapToLong(CartItem::calculatePrice) // 메서드 참조, 결과: LongStream [20000L, 20000L, 15000L]
                // CartItem::calculatePrice, 클래스명::메서드명 == item -> item.calculatePrice()(람다 표현식)
                // 각 CartItem에서 calculatePrice() 호출 → LongStream
                .sum(); // LongStream의 sum() 메서드
    }
}
