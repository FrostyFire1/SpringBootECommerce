package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
