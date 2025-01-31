package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

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

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "subdivision_id" )
    private Subdivision subdivision;

    //@JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_procedure", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private Set<Procedure> procedures = new HashSet<>();

    /*@JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private Set<Booking> bookings = new HashSet<>();*/

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, optional = true)
    private UserEntity user;
}
