package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.AppRole;
import com.frosty.SpringBootECommerce.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(AppRole role);
}
