package com.frosty.SpringBootECommerce.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
  @NotBlank private String username;
  @NotBlank @Email private String email;

  @NotBlank
  @Size(min = 7, max = 128)
  private String password;

  private Set<String> roles;
}
