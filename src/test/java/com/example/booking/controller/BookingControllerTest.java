package com.example.booking.controller;

import com.example.booking.dto.BookingDto;
import com.example.booking.dto.ClientDto;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.repository.BookingRepository;
import com.example.booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@Disabled
@WebMvcTest(BookingController.class)
@Import(TestSecurityConfig.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private BookingService bookingService;

    private static ClientDto clientDto;
    private static ProcedureDto procedureDto;
    private static EmployeeDto employeeDto;
    private static ClientDto clientDto1;
    private static ProcedureDto procedureDto1;
    private static EmployeeDto employeeDto1;

    @BeforeAll
    public static void setUp() {
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

        List<BookingDto> bills = new ArrayList<>();
        bills.add(book1);
        bills.add(book2);

        when(bookingService.getAllDto()).thenReturn(bills);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bookings/getall")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }


}
