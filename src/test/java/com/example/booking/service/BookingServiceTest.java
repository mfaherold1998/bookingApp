package com.example.booking.service;

import com.example.booking.dto.BookingDto;
import com.example.booking.dto.ClientDto;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.entity.*;
import com.example.booking.exception.NotFoundException;
import com.example.booking.mapper.BookingMapper;
import com.example.booking.repository.BookingRepository;
import com.example.booking.repository.ClientRepository;
import com.example.booking.repository.EmployeeRepository;
import com.example.booking.repository.ProcedureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Spy
    private BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);

    @InjectMocks
    private BookingService bookingService;

    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public ProcedureRepository procedureRepository;
    @Autowired
    public EmployeeRepository employeeRepository;

    private static Client client;
    private static Procedure procedure;
    private static Employee employee;
    private static Client client1;
    private static Procedure procedure1;
    private static Employee employee1;

    private static ClientDto clientDto;
    private static ProcedureDto procedureDto;
    private static EmployeeDto employeeDto;

    @BeforeAll
    public static void setUp() {
        employee = Employee.builder().build();
        procedure = Procedure.builder().name("dry").price(200.00).build();
        client = Client.builder().build();

        employee1 = Employee.builder().build();
        procedure1 = Procedure.builder().name("dry").price(200.00).build();
        client1 = Client.builder().build();

        employeeDto = EmployeeDto.builder().build();
        procedureDto = ProcedureDto.builder().name("dry").price(200.00).build();
        clientDto = ClientDto.builder().build();
    }

    @Test
    void getAllDto_with2ValidDTO_returnListOfDtoSize2() {

        Booking book1 = Booking.builder().employee(employee).procedure(procedure).client(client).build();
        Booking book2 = Booking.builder().employee(employee1).procedure(procedure1).client(client1).build();

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(book1);
        bookingList.add(book2);

        when(bookingRepository.findAll()).thenReturn(bookingList);

        List<BookingDto> response = bookingService.getAllDto();

        assertEquals(2, response.size());

    }

    @Test
    void save_withValidDTO_returnTrue() {

        Booking book = Booking.builder().employee(employee).procedure(procedure).client(client).build();
        BookingDto bookDto = BookingDto.builder().employee(employeeDto).procedure(procedureDto).client(clientDto).build();

        when(bookingRepository.saveAndFlush(ArgumentMatchers.any(Booking.class))).thenReturn(book);

        BookingDto savedDto = bookingService.save(bookDto);

        assertNotNull(savedDto);
    }

    @Test
    void save_withNonValidDTO_throwException() {

        BookingDto invalidDto = BookingDto.builder().employee(null).procedure(procedureDto).client(clientDto).build();

        when(bookingRepository.saveAndFlush(ArgumentMatchers.any(Booking.class)))
                .thenThrow(new IllegalArgumentException("Invalid BookingDto"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.save(invalidDto);
        });
    }

    @Test
    void getById_withValidDto_returnDto(){

        Long id = 1L;

        Booking book = Booking.builder().id(id).employee(employee).procedure(procedure).client(client).build();
        Optional<Booking> optBooking = Optional.of(book);

        when(bookingRepository.findById(id)).thenReturn(optBooking);

        BookingDto bookDto = bookingService.getDtoById(id);

        assertNotNull(bookDto);
        assertEquals(id, bookDto.getId());
    }

    @Test
    void getById_withNonValidDto_throwException(){
        Long id = 1L;

        when(bookingRepository.findById(id)).thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () -> {
            bookingService.getDtoById(id);
        });
    }

    @Test
    void update_withValidDto_returnTrue(){

        Long id = 1L;
        Booking book = Booking.builder().id(id).employee(employee).procedure(procedure).client(client).build();
        BookingDto bookDto = BookingDto.builder().id(id).employee(employeeDto).procedure(procedureDto).client(clientDto).build();

        when(bookingRepository.saveAndFlush(ArgumentMatchers.any(Booking.class))).thenReturn(book);
        when(bookingRepository.existsById(id)).thenReturn(true);

        BookingDto updatedBook = bookingService.update(bookDto);

        assertEquals(id, updatedBook.getId());
    }

    @Test
    void update_withNonValidDto_throwException(){
        Long id = 1L;
        BookingDto bookDto = BookingDto.builder().id(id).employee(employeeDto).procedure(procedureDto).client(clientDto).build();

        when(bookingRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            bookingService.update(bookDto);
        });
    }

    @Test
    void delete_withValidID_returnTrue(){
        Long id = 1L;
        Booking book = Booking.builder().id(id).employee(employee).procedure(procedure).client(client).build();

        when(bookingRepository.existsById(id)).thenReturn(true);
        when(bookingRepository.findById(id)).thenReturn(Optional.of(book));

        bookingService.delete(id);

        assertEquals(true, book.getDeleted());
    }

    @Test
    void delete_withNonValidID_throwException(){
        Long id = 1L;

        when(bookingRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            bookingService.delete(id);
        });
    }

    @Test
    void getAuthenticatedUser_withMockUSer_ReturnsUserEntity() {
        Long id = 1L;
        UserEntity userEntity = UserEntity.builder().id(id).build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userEntity);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        UserEntity authenticatedUser = bookingService.getAuthenticatedUser();

        assertEquals(userEntity, authenticatedUser);

        SecurityContextHolder.clearContext();
    }

}

