package com.frosty.SpringBootECommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;

    private Integer apartmentNumber;

    private Integer houseNumber;

    private String street;

    private String city;

    private String postalCode;

    private String province;

    private String country;
}
