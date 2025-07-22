package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.model.Address;
import com.frosty.SpringBootECommerce.model.User;
import com.frosty.SpringBootECommerce.payload.AddressDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.repository.AddressRepository;
import com.frosty.SpringBootECommerce.security.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final AuthUtil authUtil;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        User user = authUtil.getPrincipal();
        Address address = modelMapper.map(addressDTO, Address.class);

        address.setUser(user);
        user.getAddresses().add(address);

        address = addressRepository.save(address);

        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public ContentResponse<AddressDTO> getAllAddresses() {
        return new ContentResponse<>(addressRepository.findAll().stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList());
    }
}
