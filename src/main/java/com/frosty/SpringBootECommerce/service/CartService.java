package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.CartDTO;
import com.frosty.SpringBootECommerce.payload.CartItemDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);
}
