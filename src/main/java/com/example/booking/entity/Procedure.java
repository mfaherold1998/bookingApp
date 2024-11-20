package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Procedures")
@SQLRestriction("deleted = false")
public class Procedure extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(name = "estimated_time_in_minutes")
    private int timeInMinutes;

    @ManyToMany(mappedBy = "procedures")
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany(mappedBy = "procedures")
    private Set<Subdivision> subdivisions = new HashSet<>();

    @ManyToMany(mappedBy = "procedures")
    private Set<Booking> bookings = new HashSet<>();
}
