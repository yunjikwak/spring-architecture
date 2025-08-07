package com.example.demo.controller.page.dto;

import com.example.demo.repository.entity.vo.PaymentMethod;
import com.example.demo.repository.entity.vo.ShippingInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessOrderRequestDto {
    private String recipient;
    private String address;
    private String phone;
    private PaymentMethod paymentMethod;

    public ShippingInfo toShippingInfo() {
        return new ShippingInfo(recipient, address, phone);
    }
}
