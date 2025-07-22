package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.payload.AddressDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.service.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.createAddress(addressDTO), HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<ContentResponse<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long addressId) {
        return ResponseEntity.ok(addressService.getAddress(addressId));
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable Long addressId, @RequestBody @Valid AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, addressDTO));
    }

    @GetMapping("/addresses/user")
    public ResponseEntity<ContentResponse<AddressDTO>> getUserAddresses() {
        return ResponseEntity.ok(addressService.getUserAddresses());
    }
}
