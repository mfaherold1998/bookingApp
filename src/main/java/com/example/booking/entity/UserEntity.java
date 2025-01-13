package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Users")
@SQLRestriction("deleted = false")
public class UserEntity extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean confirmedEmail = Boolean.FALSE;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<RefreshToken> refreshTokens;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<RefreshToken> verificationCode;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
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
