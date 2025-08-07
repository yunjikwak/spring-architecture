package com.example.demo.repository;

import com.example.demo.repository.entity.Order;
import com.example.demo.repository.ports.IOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements IOrderRepositoryPort {
    private final Map<Long, Order> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(store.get(orderId));
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return store.values().stream()
                .filter(order -> customerId.equals(order.getCustomerId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            long generatedId = sequence.incrementAndGet();
            order.setId(generatedId);
        }
        store.put(order.getId(), order);
        return store.get(order.getId());
    }
}
