package com.example.demo.service;

import com.example.demo.repository.entity.Cart;
import com.example.demo.repository.entity.Product;
import com.example.demo.repository.ports.ICartRepositoryPort;
import com.example.demo.repository.ports.IProductRepositoryPort;
import com.example.demo.service.usecases.IManageCartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor // 필수 필드만 생성자 주입
public class CartService implements IManageCartUseCase { // UseCase 구현 클래스, IManageCartUseCase <- input port
    // 인터페이스(RepositoryPort-인터페이스) 각각 -> output port, 요청들을 각 객체로 만들어 저장하기 위함
    private final ICartRepositoryPort cartRepository;
    private final IProductRepositoryPort productRepository;

    @Override
    public void addProductToCart(Long customerId, Long productId, Long optionId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElse(new Cart(customerId)); // Optional에서 값이 없으면 새 Cart 객체(장바구니) 생성

        Product product = productRepository.findById(productId)
                // 상품이 존재하지 않으면 예외 발생
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.getOptionById(optionId)
                .ifPresentOrElse( // Optional 값이 존재하면
                        option -> {
                            cart.addProduct(product, option, quantity); // 옵션을 장바구니에 추가
                            cartRepository.save(cart); // 장바구니 저장
                        },
                        // Optional 값이 없으면 예외 발생
                        () -> { throw new IllegalArgumentException("상품 옵션을 찾을 수 없습니다."); }
                );
    }

    @Override // IManageCartUseCase 메서드 구현Override
    public Optional<Cart> getCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }
}