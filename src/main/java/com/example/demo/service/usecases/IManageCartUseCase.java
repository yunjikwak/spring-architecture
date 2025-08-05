package com.example.demo.service.usecases;

import com.example.demo.repository.entity.Cart;
import com.example.demo.service.IInputPort;

import java.util.Optional;

public interface IManageCartUseCase extends IInputPort { // UseCase 인터페이스, IInputPort <- input port 의미, 실제 존재 X
    void addProductToCart(Long customerId, Long productId, Long optionId, int quantity);
    Optional<Cart> getCart(Long customerId);
}
