package com.example.demo.repository.entity;

import com.example.demo.message.event.OrderPaidEvent;
import com.example.demo.repository.entity.vo.OrderItem;
import com.example.demo.repository.entity.vo.OrderStatus;
import com.example.demo.repository.entity.vo.ShippingInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class Order extends AggregateRoot {
    @Setter
    private Long id;
    private final Long customerId;
    private final List<OrderItem> orderItems;
    private long originalPrice;
    private long discountedPrice;
    private final ShippingInfo shippingInfo;
    private OrderStatus status;
    private final LocalDateTime orderDate;

    public Order(Long customerId, List<OrderItem> orderItems, ShippingInfo shippingInfo) {
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.shippingInfo = shippingInfo;
        this.status = OrderStatus.PAYMENT_READY;
        this.orderDate = LocalDateTime.now();
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        this.originalPrice = orderItems.stream().mapToLong(OrderItem::calculatePrice).sum();
        this.discountedPrice = this.originalPrice;
    }

    public void applyCoupon(Coupon coupon) {
        if (coupon == null) return;
        long discountAmount = coupon.calculateDiscount(this.originalPrice);
        long priceAfterDiscount = this.originalPrice - discountAmount;
        this.discountedPrice = Math.round(priceAfterDiscount / 100.0) * 100;
    }

    public void paymentComplete() {
        if (this.status != OrderStatus.PAYMENT_READY) {
            throw new IllegalStateException("결제 대기 상태에서만 결제 완료가 가능합니다.");
        }
        this.status = OrderStatus.PAYMENT_COMPLETE;
        this.addDomainEvent(new OrderPaidEvent(this.id));
    }

    public void startShipping() {
        if (this.status != OrderStatus.PAYMENT_COMPLETE) {
            throw new IllegalStateException("결제가 완료되어야 배송을 시작할 수 있습니다.");
        }
        this.status = OrderStatus.SHIPPING_READY;
    }
}
