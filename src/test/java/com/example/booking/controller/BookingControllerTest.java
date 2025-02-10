package com.example.booking.controller;


import com.example.booking.configuration.AuthEntryPointConfig;
import com.example.booking.configuration.JwtAuthFilterConfig;
import com.example.booking.configuration.SecurityConfig;
import com.example.booking.dto.BookingDto;
import com.example.booking.dto.ClientDto;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.repository.BookingRepository;
import com.example.booking.service.BookingService;
import com.example.booking.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

//@Disabled
@WebMvcTest(BookingController.class)
@Import(SecurityConfig.class)
@WithMockUser(username = "nanda")
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @MockBean
    JwtAuthFilterConfig jwtAuthFilterConfig;

    @MockBean
    AuthEntryPointConfig authEntryPointConfig;

    private ClientDto clientDto;
    private ProcedureDto procedureDto;
    private EmployeeDto employeeDto;
    private ClientDto clientDto1;
    private ProcedureDto procedureDto1;
    private EmployeeDto employeeDto1;

    @BeforeEach
    public void setUp() {
        employeeDto = EmployeeDto.builder().build();
        procedureDto = ProcedureDto.builder().name("dry").price(200.00).build();
        clientDto = ClientDto.builder().build();
        employeeDto1 = EmployeeDto.builder().build();
        procedureDto1 = ProcedureDto.builder().name("dry").price(200.00).build();
        clientDto1 = ClientDto.builder().build();
    }

    @Test
    void getAll_with2ValidBookings_returnOk() throws Exception {

        BookingDto book1 = BookingDto.builder().employee(employeeDto).procedure(procedureDto).client(clientDto).build();
        BookingDto book2 = BookingDto.builder().employee(employeeDto1).procedure(procedureDto1).client(clientDto1).build();

        List<BookingDto> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bookingService.getAllDto()).thenReturn(books);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bookings/getall")
                        .accept(MediaType.APPLICATION_JSON)
                )
                //.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

}
