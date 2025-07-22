package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Id(Long userId);
}
