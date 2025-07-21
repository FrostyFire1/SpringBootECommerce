package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);
}
