package ru.voter.restaurantvote.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
public class User extends BaseEntity {

    @NotEmpty
    @Size(max = 128)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(max = 128)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 128)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 256)
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
