package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmailIgnoreCase(String email);
}
