package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.AddressDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    ContentResponse<AddressDTO> getAllAddresses();

    AddressDTO getAddress(Long addressId);

    ContentResponse<AddressDTO> getUserAddresses();
}
