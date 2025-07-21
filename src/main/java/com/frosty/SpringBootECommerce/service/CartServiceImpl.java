package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Cart;
import com.frosty.SpringBootECommerce.model.CartItem;
import com.frosty.SpringBootECommerce.model.Product;
import com.frosty.SpringBootECommerce.payload.APIResponse;
import com.frosty.SpringBootECommerce.payload.CartDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.repository.CartItemRepository;
import com.frosty.SpringBootECommerce.repository.CartRepository;
import com.frosty.SpringBootECommerce.repository.ProductRepository;
import com.frosty.SpringBootECommerce.security.util.AuthUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final AuthUtil authUtil;

    private final ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = cartRepository
                .findByUser_Email(authUtil.getPrincipalEmail())
                .orElseGet(() -> new Cart(0.0, authUtil.getPrincipal()));

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (cartItemRepository
                .findByProduct_Id_AndCart_Id(productId, cart.getId())
                .isPresent()) {
            throw new APIException("Item already in cart");
        }

        Integer productQuantity = product.getQuantity();
        if (productQuantity == 0) {
            throw new APIException(product.getName() + " is not available");
        }
        if (productQuantity < quantity) {
            throw new APIException("You can only order up to " + productQuantity + " of " + product.getName());
        }
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
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
        // Can do it now, but this application will reduce the quantity when the order is placed
        // product.setQuantity(productQuantity - quantity);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setProducts(getProductsFromCart(cart));
        return cartDTO;
    }

    @Override
    public ContentResponse<CartDTO> getAllCarts() {
        List<CartDTO> cartDTOS = cartRepository.findAll().stream()
                .map(cart -> {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                    cartDTO.setProducts(getProductsFromCart(cart));
                    return cartDTO;
                })
                .toList();
        if (cartDTOS.isEmpty()) {
            throw new APIException("No carts found");
        }
        return ContentResponse.<CartDTO>builder().content(cartDTOS).build();
    }

    public Set<ProductDTO> getProductsFromCart(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> {
                    ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public CartDTO getUserCartDTO() {
        Cart cart = cartRepository
                .findByUser_Email(authUtil.getPrincipalEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "email", authUtil.getPrincipalEmail()));
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setProducts(getProductsFromCart(cart));
        return cartDTO;
    }

    //    @Transactional
    //    @Override
    //    public CartDTO updateProductInCart(Long productId, String operation) {
    //
    //        String emailId = authUtil.getPrincipalEmail();
    //        Cart userCart = cartRepository.findByUser_Email(emailId).get();
    //        Long cartId  = userCart.getId();
    //
    //        Cart cart = cartRepository.findById(cartId)
    //                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
    //
    //        Product product = productRepository.findById(productId)
    //                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    //
    //        CartItem cartItem = cartItemRepository.findByProduct_Id_AndCart_Id(cartId, productId)
    //                .orElseThrow(() -> new APIException("Product " + product.getName() + " not available in the
    // cart!!!"));
    //
    //        Integer quantity =  cartItem.getQuantity();
    //        try{
    //            quantity += Integer.parseInt(operation);
    //        } catch (NumberFormatException e) {
    //            throw new APIException("Couldn't parse operation");
    //        }
    //
    //        if (product.getQuantity() == 0) {
    //            throw new APIException(product.getName() + " is not available");
    //        }
    //
    //        if (product.getQuantity() < quantity) {
    //            throw new APIException("Please, make an order of the " + product.getName()
    //                    + " less than or equal to the quantity " + product.getQuantity() + ".");
    //        }
    //
    //        cartItem.setProductPrice(product.getSpecialPrice());
    //        cartItem.setQuantity(quantity);
    //        cartItem.setDiscount(product.getDiscount());
    //        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
    //        cartRepository.save(cart);
    //        CartItem updatedItem = cartItemRepository.save(cartItem);
    //        if(updatedItem.getQuantity() == 0){
    //            cartItemRepository.deleteById(updatedItem.getId());
    //        }
    //
    //
    //        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
    //
    //        Set<CartItem> cartItems = cart.getCartItems();
    //
    //        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
    //            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
    //            prd.setQuantity(item.getQuantity());
    //            return prd;
    //        });
    //
    //
    //        cartDTO.setProducts(productStream.collect(Collectors.toSet()));
    //
    //        return cartDTO;
    //    }

    @Override
    @Transactional
    public CartDTO updateProductInCart(Long productId, String operation) {
        Cart cart = cartRepository
                .findByUser_Email(authUtil.getPrincipalEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "email", authUtil.getPrincipalEmail()));
        CartItem cartItem = cartItemRepository
                .findByProduct_Id_AndCart_Id(productId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product in cart", "productId", productId));

        Integer newQuantity = cartItem.getQuantity();
        Integer cartItemQuantity = cartItem.getQuantity();
        try {
            newQuantity += Integer.parseInt(operation);
        } catch (NumberFormatException e) {
            if (!operation.equalsIgnoreCase("delete")) {
                throw new APIException("Couldn't parse operation");
            }
            newQuantity = 0;
        }

        if (newQuantity.equals(cartItem.getQuantity())) {
            throw new APIException("Resulting quantity is the same.");
        }

        Product product = cartItem.getProduct();
        Integer productQuantity = product.getQuantity();
        if (productQuantity < newQuantity) {
            throw new APIException("You can only order up to " + productQuantity + " of " + product.getName());
        }

        cartItem.setQuantity(newQuantity);
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * (newQuantity - cartItemQuantity)));
        cart = cartRepository.save(cart);
        cartItem = cartItemRepository.save(cartItem);

        if (cartItem.getQuantity() == 0 || operation.equalsIgnoreCase("delete")) {
            this.deleteItemFromCart(cart.getId(), productId);
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setProducts(getProductsFromCart(cart));
        return cartDTO;
    }

    @Override
    @Transactional
    public APIResponse deleteItemFromCart(Long cartId, Long productId) {
        cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));

        CartItem cartItem = cartItemRepository
                .findByProduct_Id_AndCart_Id(productId, cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Product in cart", "productId", productId));
        cartItemRepository.deleteByCart_IdAndProduct_Id(cartId, productId);

        return new APIResponse(cartItem.getProduct().getName() + " removed from the cart.", true);
    }
}
