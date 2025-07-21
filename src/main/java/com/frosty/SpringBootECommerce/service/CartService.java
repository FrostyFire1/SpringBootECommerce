package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.CartDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    ContentResponse<CartDTO> getAllCarts();
}
