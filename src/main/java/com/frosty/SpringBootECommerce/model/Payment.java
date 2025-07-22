package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            mappedBy = "payment",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order order;

    @NotBlank
    private String paymentMethod;

    private String pgPaymentId;
    private String pgPaymentStatus;
    private String pgResponse;
    private String pgName;

    public Payment(String paymentMethod, String pgPaymentId, String pgPaymentStatus, String pgResponse, String pgName) {
        this.paymentMethod = paymentMethod;
        this.pgPaymentId = pgPaymentId;
        this.pgPaymentStatus = pgPaymentStatus;
        this.pgResponse = pgResponse;
        this.pgName = pgName;
    }
}
