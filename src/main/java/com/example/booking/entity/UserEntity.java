package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Table(name = "Users")
@SQLRestriction("deleted = false")
public class UserEntity extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private Boolean confirmedEmail = Boolean.FALSE;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<RefreshToken> refreshTokens;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<VerificationCode> verificationCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Invitation> invitations;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = true, unique = true)
    @JsonBackReference
    private Client client;

    @OneToOne
    @JoinColumn(name = "proprietor_id", nullable = true, unique = true)
    @JsonBackReference
    private Proprietor proprietor;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = true, unique = true)
    @JsonBackReference
    private Employee employee;


    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(getRole().getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {return !this.isDeleted;}
}
