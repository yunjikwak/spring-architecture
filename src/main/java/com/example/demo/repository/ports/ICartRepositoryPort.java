package com.example.demo.repository.ports;

import com.example.demo.repository.IOutputPort;
import com.example.demo.repository.entity.Cart;

import java.util.List;
import java.util.Optional;

// ICartRepositoryPort: CartRepository의 인터페이스, Output Port
// IOutputPort: Output Port를 의미함 실제 의미 X
public interface ICartRepositoryPort extends IOutputPort {
    Optional<Cart> findByCustomerId(Long customerId);
    Cart save(Cart cart);
}