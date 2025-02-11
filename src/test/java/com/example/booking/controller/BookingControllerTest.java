package com.example.booking.controller;

import com.example.booking.configuration.AuthEntryPointConfig;
import com.example.booking.configuration.SecurityConfig;
import com.example.booking.dto.BookingDto;
import com.example.booking.service.BookingService;
import com.example.booking.utils.Enums;
import com.example.booking.utils.JwtUtils;
import com.example.booking.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private AuthEntryPointConfig authEntryPointConfig;


    @Test
    public void callGetAllBookings() throws Exception {
        var allRoles = TestUtils.getAllRoles();
        for (var role : allRoles) {
            var mockedUser = TestUtils.mockUserAuthenticationWithRole(role, jwtUtils, userDetailsService, bookingService);
            var result = getAllBookings();
            if (role.equals(Enums.RoleNames.CLIENT)){
                result
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()").value(2));
                System.out.printf("User with role {%s} access%n", role);
            } else {
                System.out.printf("User with role {%s} denied%n", role);
                result.andExpect(status().is4xxClientError());
            }
        }
    }


    private ResultActions getAllBookings() throws Exception {
        BookingDto booking1 = BookingDto.builder().build();
        BookingDto booking2 = BookingDto.builder().build();
        List<BookingDto> mockBookings = Arrays.asList(booking1, booking2);
        when(bookingService.getAllDto()).thenReturn(mockBookings);
        return mockMvc.perform(get("/api/bookings/getall"));
    }
}
