package com.example.booking.utils;

import com.example.booking.common.BaseService;
import com.example.booking.entity.RoleEntity;
import com.example.booking.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TestUtils {

    public static Set<Enums.RoleNames> getAllRoles(){
        return Set.of(
                Enums.RoleNames.SUPERADMIN,
                Enums.RoleNames.PROPRIETOR,
                Enums.RoleNames.EMPLOYEE,
                Enums.RoleNames.CLIENT
        );
    }

    public static UserEntity getMockedUserWithRoles(Set<Enums.RoleNames> roleNames) {
        var mockedRoles = roleNames.stream()
                .map(roleName ->
                        RoleEntity.builder()
                        .name(roleName.getValue())
                        .build())
                .collect(Collectors.toSet());
        return UserEntity.builder()
                .email("user@example.com")
                .password("password")
                .roles(mockedRoles)
                .build();
    }

    public static UserEntity mockUserAuthenticationWithRoles(
            Set<Enums.RoleNames> roleNames,
            JwtUtils jwtUtils,
            UserDetailsService userDetailsService,
            BaseService service
    ){
        var mockedUser = TestUtils.getMockedUserWithRoles(roleNames);
        when(jwtUtils.extractSubject(any())).thenReturn(mockedUser.getEmail());
        when(jwtUtils.isTokenValid(any())).thenReturn(Boolean.TRUE);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mockedUser);
        when(service.getAuthenticatedUser()).thenReturn(mockedUser);
        return mockedUser;
    }

    public static UserEntity mockUserAuthenticationWithRole(
            Enums.RoleNames roleName,
            JwtUtils jwtUtils,
            UserDetailsService userDetailsService,
            BaseService service
    ){
        var mockedUser = TestUtils.getMockedUserWithRoles(Set.of(roleName));
        when(jwtUtils.extractSubject(any())).thenReturn(mockedUser.getEmail());
        when(jwtUtils.isTokenValid(any())).thenReturn(Boolean.TRUE);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mockedUser);
        when(service.getAuthenticatedUser()).thenReturn(mockedUser);
        return mockedUser;
    }
}
