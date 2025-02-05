package com.example.booking.repository;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Client;
import com.example.booking.entity.Employee;
import com.example.booking.entity.Procedure;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingRepositoryTest {

    @Autowired
    public BookingRepository bookingRepository;
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

    @BeforeAll
    public static void setUp() {
        employee = Employee.builder().build();
        procedure = Procedure.builder().name("dry").price(200.00).build();
        client = Client.builder().build();

        employee1 = Employee.builder().build();
        procedure1 = Procedure.builder().name("dry").price(200.00).build();
        client1 = Client.builder().build();
    }

    @Test
    @Transactional
    void saveAndFlush_withValidBooking_returnTrue() {

        clientRepository.save(client);
        procedureRepository.save(procedure);
        employeeRepository.save(employee);

        Booking lastBook = bookingRepository.findFirstByOrderByIdDesc().orElse(Booking.builder().id(0L).build());

        Booking book = Booking.builder().employee(employee).procedure(procedure).client(client).build();

        Booking savedBook = bookingRepository.saveAndFlush(book);

        assertNotNull(savedBook);
        assertEquals(lastBook.getId() + 1, (long) savedBook.getId());
    }

    @Test
    @Transactional
    void saveAndFlush_withNotValidBooking_throwsException() {

        clientRepository.save(client);
        procedureRepository.save(procedure);
        employeeRepository.save(employee);

        Booking lastBook = bookingRepository.findFirstByOrderByIdDesc().orElse(Booking.builder().id(0L).build());

        Booking book = Booking.builder().build();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            bookingRepository.saveAndFlush(book);
        });
    }

    @Test
    void findAll_withTwoValidBookings_returnListOfBookingsSize2() {

        clientRepository.save(client);
        procedureRepository.save(procedure);
        employeeRepository.save(employee);
        clientRepository.save(client1);
        procedureRepository.save(procedure1);
        employeeRepository.save(employee1);

        Booking book1 = Booking.builder().employee(employee).procedure(procedure).client(client).build();
        Booking book2 = Booking.builder().employee(employee1).procedure(procedure1).client(client1).build();

        List<Booking> booksBefore = bookingRepository.findAll();

        bookingRepository.save(book1);
        bookingRepository.save(book2);

        List<Booking> booksNow = bookingRepository.findAll();

        assertEquals(booksBefore.size()+2 , booksNow.size());
    }

    @Test
    @Transactional
    void deleteById_withValidBook_returnTrue(){

        clientRepository.save(client);
        procedureRepository.save(procedure);
        employeeRepository.save(employee);

        Booking lastBook = bookingRepository.findFirstByOrderByIdDesc().orElse(Booking.builder().id(0L).build());
        Booking book = Booking.builder().employee(employee).procedure(procedure).client(client).build();

        Booking savedBook = bookingRepository.saveAndFlush(book);
        List<Booking> booksBefore = bookingRepository.findAll();

        bookingRepository.deleteById(lastBook.getId()+1);
        List<Booking> booksNow = bookingRepository.findAll();

        assertEquals(booksBefore.size()-1, booksNow.size());
        assertDoesNotThrow(() -> {
            bookingRepository.deleteById(lastBook.getId()+1);
        });

    }

}
