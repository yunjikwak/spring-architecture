package com.example.demo.repository;

import com.example.demo.repository.entity.Category;
import com.example.demo.repository.entity.Product;
import com.example.demo.repository.entity.ProductOption;
import com.example.demo.repository.ports.IProductRepositoryPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor // 필수 필드만 생성자 주입
public class ProductRepository implements IProductRepositoryPort {
    private static final Map<Long, Product> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @PostConstruct // 의존성 주입 후 자동 실행되도록 함(초기화용)
    public void init() {
        Category laptop = new Category(1L, "노트북");
        Category toothpaste = new Category(2L, "치약");

        save(new Product(101L, "맥북 프로 14인치", "https://thumbnail6.coupangcdn.com/thumbnails/remote/492x492ex/image/rs_quotation_api/vkgxk2g3/2da24f6eafe44bb997f620e65c6302ee.jpg", laptop, List.of(
                new ProductOption(1001L, "M3 8GB 512GB 스페이스 그레이", 2190000L, 10),
                new ProductOption(1002L, "M3 16GB 1TB 스페이스 그레이", 2890000L, 5)
        )));
        save(new Product(102L, "LG 그램 16인치", "https://thumbnail8.coupangcdn.com/thumbnails/remote/492x492ex/image/vendor_inventory/dff5/56824e84c04d5c27342c161a0bd463ec7cc5394cd339e7e8b8d8548c72e7.jpg", laptop, List.of(
                new ProductOption(2001L, "i5 8GB 256GB 화이트", 1370000L, 20),
                new ProductOption(2002L, "i5 16GB 256GB 화이트", 1590000L, 10)
        )));
        save(new Product(103L, "덴티스테 플러스화이트 치약", "https://thumbnail9.coupangcdn.com/thumbnails/remote/492x492ex/image/vendor_inventory/3ec6/31f73ce367d50f83acb999d7463f2d502341d29a1f49fba1550d2b6228d0.jpg", toothpaste, List.of(
                new ProductOption(3001L, "100g x 3개", 18900L, 100),
                new ProductOption(3002L, "160g x 2개", 21000L, 80)
        )));
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(store.get(productId));
    } // Optional.ofNullable: null일 수도 있는 값을 감싸는 Optional 객체 생성

    @Override
    public List<Product> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        return store.values().stream()
                // toLowerCase() -> 상품 이름 소문자로 변경, 키워드도 변경하여 같은 조건 하에 포함 여부 검사
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList()); // 필터링된 Product들로 새로운 List<Product> 생성
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            long generatedId = sequence.incrementAndGet();
            product.setId(generatedId);
        }
        store.put(product.getId(), product);
        return store.get(product.getId());
    }
}