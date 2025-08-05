package com.example.demo.repository;

import com.example.demo.repository.entity.Cart;
import com.example.demo.repository.entity.Order;
import com.example.demo.repository.entity.Product;
import com.example.demo.repository.ports.ICartRepositoryPort;
import com.example.demo.repository.ports.IOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor // 필수 필드만 생성자 주입
public class CartRepository implements ICartRepositoryPort { // Secondary Adapter: 외부로 요청이 나감, ICartRepositoryPort <- output port
    private final Map<Long, Cart> store = new HashMap<>(); // 장바구니Cart를 저장할 HashMap(인메모리 저장소)<key, value>
    private final AtomicLong sequence = new AtomicLong(0); //  ID 자동 생성

    @Override
    public Optional<Cart> findByCustomerId(Long customerId) {
        // store.values().stream() // store의 모든 Cart 객체를 스트림으로 변환
        // .filter((each) -> customerId.equals(each.getCustomerId())) // 각 Cart의 customerId와 비교
        // .findFirst(); // 첫 번째 일치하는 Cart를 Optional로 반환
        return store.values().stream().filter((each) -> customerId.equals(each.getCustomerId())).findFirst();
    }

    @Override
    public Cart save(Cart cart) {
        if (cart.getId() == null) {
            long generatedId = sequence.incrementAndGet(); // incrementAndGet: AtomicLong 객체의 값을 1 증가시키고, 증가된 값을 반환
            cart.setId(generatedId);
        }
        store.put(cart.getId(), cart);
        return store.get(cart.getId());
        // return 하는 이유
        // Spring Data JPA의 표준
        //public interface JpaRepository<T, ID> {
        //    <S extends T> S save(S entity); // 저장된 엔티티 반환
        //}
    }
}