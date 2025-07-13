package com.example.demo.repository.ports;

import com.example.demo.repository.IOutputPort;
import com.example.demo.repository.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface ICartRepositoryPort extends IOutputPort {
    Optional<Cart> findByCustomerId(Long customerId);
    Cart save(Cart cart);
}