package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}
