package com.frosty.SpringBootECommerce.security.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
  private Long id;
  private String username;
  private List<String> roles;
}
