package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Address;
import com.frosty.SpringBootECommerce.model.User;
import com.frosty.SpringBootECommerce.payload.AddressDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.repository.AddressRepository;
import com.frosty.SpringBootECommerce.repository.UserRepository;
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
    private final UserRepository userRepository;

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

    @Override
    public AddressDTO getAddress(Long addressId) {
        Address address = addressRepository
                .findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public ContentResponse<AddressDTO> getUserAddresses() {
        User user = authUtil.getPrincipal();
        return new ContentResponse<>(addressRepository.findByUser_Id(user.getId()).stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList());
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository
                .findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        Address newAddress = modelMapper.map(addressDTO, Address.class);

        User user = address.getUser();
        newAddress.setUser(user);
        newAddress.setId(addressId);
        newAddress = addressRepository.save(newAddress);

        user.getAddresses().removeIf(a -> a.getId().equals(addressId));
        user.getAddresses().add(newAddress);
        userRepository.save(user);

        return modelMapper.map(newAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO deleteAddress(Long addressId) {
        Address address = addressRepository
                .findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        User user = address.getUser();
        user.getAddresses().remove(address);

        userRepository.save(user);
        addressRepository.deleteById(addressId);

        return modelMapper.map(address, AddressDTO.class);
    }
}
