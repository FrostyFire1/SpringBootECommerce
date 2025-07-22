package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.payload.OrderDTO;
import com.frosty.SpringBootECommerce.payload.OrderRequestDTO;
import com.frosty.SpringBootECommerce.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(
            @PathVariable String paymentMethod, @RequestBody OrderRequestDTO orderRequestDTO) {
        return new ResponseEntity<>(orderService.placeOrder(paymentMethod, orderRequestDTO), HttpStatus.CREATED);
    }
}
