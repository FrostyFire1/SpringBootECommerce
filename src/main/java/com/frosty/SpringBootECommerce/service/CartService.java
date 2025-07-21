package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.APIResponse;
import com.frosty.SpringBootECommerce.payload.CartDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    ContentResponse<CartDTO> getAllCarts();

    CartDTO getUserCartDTO();

    CartDTO updateProductQuantityInCart(Long productId, String operation);

    APIResponse deleteItemFromCart(Long cartId, Long productId);

    void updateProductInCart(Long id, Long productId);
}
