package com.frosty.SpringBootECommerce;

import com.frosty.SpringBootECommerce.model.Category;
import com.frosty.SpringBootECommerce.model.Product;
import com.frosty.SpringBootECommerce.repository.CategoryRepository;
import com.frosty.SpringBootECommerce.repository.ProductRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private ProductRepository productRepository;

  @Bean
  public CommandLineRunner initDatabase() {
    return args -> {
      Category category = new Category(null, "Suits", new ArrayList<>());
      categoryRepository.save(category);
      productRepository.save(
          new Product(
              null,
              "Blue Suit No Tie",
              "It's a suit without a tie (duh)",
              1,
              100.00,
              10.0,
              90.00,
              "whatever",
              category,
              null,
              null));
    };
  }
}
