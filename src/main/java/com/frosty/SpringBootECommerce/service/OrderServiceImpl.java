package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.*;
import com.frosty.SpringBootECommerce.payload.OrderDTO;
import com.frosty.SpringBootECommerce.payload.OrderItemDTO;
import com.frosty.SpringBootECommerce.payload.OrderRequestDTO;
import com.frosty.SpringBootECommerce.repository.*;
import com.frosty.SpringBootECommerce.security.util.AuthUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthUtil authUtil;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(String paymentMethod, OrderRequestDTO orderRequestDTO) {
        String email = authUtil.getPrincipalEmail();
        Cart cart = cartRepository
                .findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cart ", "email", email));

        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new APIException("Cart is empty");
        }
        Address address = addressRepository
                .findById(orderRequestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address ", "id", orderRequestDTO.getAddressId()));

        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Accepted");
        order.setAddress(address);

        Payment payment = new Payment(
                paymentMethod,
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgPaymentStatus(),
                orderRequestDTO.getPgResponse(),
                orderRequestDTO.getPgName());
        payment.setOrder(order);
        payment = paymentRepository.save(payment);

        order.setPayment(payment);
        order = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderItemPrice(cartItem.getProductPrice() * cartItem.getQuantity());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        orderItems = orderItemRepository.saveAll(orderItems);

        cartItems.forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            cartService.deleteItemFromCart(cart.getId(), product.getId());
        });

        OrderDTO summary = modelMapper.map(order, OrderDTO.class);
        summary.setOrderItems(orderItems.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                .toList());

        summary.setAddressId(orderRequestDTO.getAddressId());
        return summary;
    }
}
