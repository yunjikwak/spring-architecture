package com.example.demo.repository.entity.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShippingInfo {
    public final String recipient;
    public final String address;
    public final String phone;
}
