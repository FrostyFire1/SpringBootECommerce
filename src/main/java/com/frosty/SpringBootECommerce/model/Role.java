package com.frosty.SpringBootECommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
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

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @JsonIgnore
    private Set<User> users;
}
