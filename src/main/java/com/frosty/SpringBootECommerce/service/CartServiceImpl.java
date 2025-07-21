package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Cart;
import com.frosty.SpringBootECommerce.model.CartItem;
import com.frosty.SpringBootECommerce.model.Product;
import com.frosty.SpringBootECommerce.payload.CartDTO;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.repository.CartItemRepository;
import com.frosty.SpringBootECommerce.repository.CartRepository;
import com.frosty.SpringBootECommerce.repository.ProductRepository;
import com.frosty.SpringBootECommerce.security.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = this.getUserCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));

        if(cartItemRepository.findByProduct_Id_AndCart_Id(productId, cart.getId())
                .isPresent()){
            throw new APIException("Item already in cart");
        }

        Integer productQuantity = product.getQuantity();
        if(productQuantity == 0){
            throw new APIException(product.getName() + " is not available");
        }
        if(productQuantity < quantity){
            throw new APIException("You can only order up to " + productQuantity + " of " + product.getName());
        }
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice()*quantity));
        cart = cartRepository.save(cart);

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = cartItemRepository.save(CartItem.builder()
                .product(product)
                .quantity(quantity)
                .cart(cart)
                .discount(product.getDiscount())
                .productPrice(product.getSpecialPrice())
                .build());
        cartItems.add(cartItem);
        //Can do it now, but this application will reduce the quantity when the order is placed
        // product.setQuantity(productQuantity - quantity);

        Set<ProductDTO> productDTOS = cart.getCartItems()
                .stream()
                .map(item -> {
                    ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toSet());

        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    public Cart getUserCart(){
        return cartRepository.findByUser_Email(authUtil.getPrincipalEmail())
                .orElse(new Cart(0.0, authUtil.getPrincipal()));

    }
}
