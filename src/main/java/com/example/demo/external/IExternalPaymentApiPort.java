package com.example.demo.external;

import com.example.demo.repository.entity.Order;
import com.example.demo.repository.entity.vo.PaymentMethod;

public interface IExternalPaymentApiPort {
    boolean processPayment(Order order, PaymentMethod paymentMethod);
}
