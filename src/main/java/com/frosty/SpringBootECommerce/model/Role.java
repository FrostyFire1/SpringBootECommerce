package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  @ToString.Exclude
  private AppRole role;

  public Role(AppRole appRole) {
    this.role = appRole;
  }
}
