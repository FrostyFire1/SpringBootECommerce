package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.OrderDTO;
import com.frosty.SpringBootECommerce.payload.OrderRequestDTO;

public interface OrderService {
    OrderDTO placeOrder(String paymentMethod, OrderRequestDTO orderRequestDTO);
}
