package com.frosty.SpringBootECommerce.payload;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    private String paymentMethod;
    private String pgPaymentId;
    private String pgPaymentStatus;
    private String pgResponse;
    private String pgName;
}
