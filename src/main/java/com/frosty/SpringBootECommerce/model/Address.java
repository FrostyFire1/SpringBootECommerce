package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer apartmentNumber;

    @NotNull
    private Integer houseNumber;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String province;

    @NotBlank
    private String country;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    public Address(
            int apartmentNumber,
            int houseNumber,
            String street,
            String city,
            String postalCode,
            String province,
            String country) {
        this.apartmentNumber = apartmentNumber;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
    }
}
