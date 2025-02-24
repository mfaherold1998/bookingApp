package com.example.booking.controller;

import com.example.booking.configuration.AuthEntryPointConfig;
import com.example.booking.configuration.SecurityConfig;
import com.example.booking.dto.BookingDto;
import com.example.booking.dto.ClientDto;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.service.BookingService;
import com.example.booking.utils.Enums;
import com.example.booking.utils.JwtUtils;
import com.example.booking.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Disabled
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
    public void getAll_withAllUsersRoles_returnListJustForSuperadmin() throws Exception {

        var allRoles = TestUtils.getAllRoles();

        BookingDto bookDto1 = BookingDto.builder().build();
        BookingDto bookDto2 = BookingDto.builder().build();
        List<BookingDto> mockBookings = Arrays.asList(bookDto1, bookDto2);

        for (var role : allRoles) {

            var mockedUser = TestUtils.mockUserAuthenticationWithRole(role, jwtUtils, userDetailsService);

            when(bookingService.getAllDto()).thenReturn(mockBookings);

            var result = mockMvc.perform(get("/api/bookings/getall"));

            if (role.equals(Enums.RoleNames.SUPERADMIN)){
                result
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()").value(2));
                System.out.printf("User with role {%s} access%n", role);
            }

            else {
                System.out.printf("User with role {%s} denied%n", role);
                result.andExpect(status().is4xxClientError());
            }
        }
    }

    @Test
    public void getById_withAllUsersRoles_returnOkForAllRoles() throws Exception {

        var allRoles = TestUtils.getAllRoles();
        Long id = 1L;
        BookingDto bookDto = BookingDto.builder().id(id).build();

        for (var role : allRoles) {

            var mockedUser = TestUtils.mockUserAuthenticationWithRole(role, jwtUtils, userDetailsService);

            when(bookingService.getDtoById(id)).thenReturn(bookDto);

            var result = mockMvc.perform(get("/api/bookings/get/"+id));

            result
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.id").value(1));

        }
    }

    @Test
    public void save_withAllUsersRoles_returnOkForAllRoles() throws Exception {

        var allRoles = TestUtils.getAllRoles();

        Long id = 1L;

        var employeeDto = EmployeeDto.builder().id(id).firstName("maria").lastName("artigas").email("maria@gmail.com").build();
        var procedureDto = ProcedureDto.builder().id(id).name("dry").price(200.00).build();
        var clientDto = ClientDto.builder().id(id).firstName("federico").lastName("calabro").email("federico@gmail.com").build();

        BookingDto bookDto = BookingDto.builder().id(id).employee(employeeDto).procedure(procedureDto).client(clientDto).build();

        for (var role : allRoles) {

            var mockedUser = TestUtils.mockUserAuthenticationWithRole(role, jwtUtils, userDetailsService);

            when(bookingService.save(any(BookingDto.class))).thenReturn(bookDto);

            var result = mockMvc.perform(post("/api/bookings/save")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(bookDto)));

            result
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$.id").value(1));

        }
    }

    @Test
    public void delete_withAllUsersRoles_returnOkForAllRoles() throws Exception{

        var allRoles = TestUtils.getAllRoles();
        Long id = 1L;
        BookingDto bookDto = BookingDto.builder().id(id).build();

        for (var role : allRoles) {

            var mockedUser = TestUtils.mockUserAuthenticationWithRole(role, jwtUtils, userDetailsService);

            when(bookingService.delete(any(Long.class))).thenReturn(Boolean.TRUE);

            var result = mockMvc.perform(delete("/api/bookings/delete/"+id));

            result
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(true));

        }
    }

    @Test
    public void update_withAllUsersRoles_returnOkForAllRoles() throws Exception {

        var allRoles = TestUtils.getAllRoles();

        Long id = 1L;

        var employeeDto = EmployeeDto.builder().id(id).firstName("maria").lastName("artigas").email("maria@gmail.com").build();
        var procedureDto = ProcedureDto.builder().id(id).name("dry").price(200.00).build();
        var clientDto = ClientDto.builder().id(id).firstName("federico").lastName("calabro").email("federico@gmail.com").build();

        BookingDto bookDto = BookingDto.builder().id(id).employee(employeeDto).procedure(procedureDto).client(clientDto).build();

        for (var role : allRoles) {

            var mockedUser = TestUtils.mockUserAuthenticationWithRole(role, jwtUtils, userDetailsService);

            when(bookingService.update(any(BookingDto.class))).thenReturn(bookDto);

            var result = mockMvc.perform(put("/api/bookings/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(bookDto)));

            result
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$.id").value(1));

        }
    }

}
