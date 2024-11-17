package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "Employees")
@SQLRestriction("deleted = false")
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    private String email;

    @OneToOne
    @JoinColumn(name = "agenda_id")
    private EmployeeAgenda agenda;

    @ManyToOne
    @JoinColumn(name = "subdivision_id" )
    private Subdivision subdivision;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_procedure", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private List<Procedure> procedures = Collections.emptyList();

    @OneToMany(mappedBy = "employee")
    private List<Booking> bookings = Collections.emptyList();
}
