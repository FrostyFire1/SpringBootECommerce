package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(min = 5, max = 32)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 7, max = 128)
    private String password;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Address> addresses = new LinkedHashSet<>();

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true,
            mappedBy = "seller")
    private Set<Product> products;

    @ToString.Exclude
    @OneToOne(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private Cart cart;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id)
                && Objects.equals(username, user.username)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(roles, user.roles)
                && Objects.equals(addresses, user.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, roles);
    }
}
