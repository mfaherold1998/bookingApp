package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

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

    /*@JsonBackReference
    @ManyToMany(mappedBy = "procedures")
    private Set<Employee> employees = new HashSet<>();*/

    @JsonBackReference
    @ManyToMany(mappedBy = "procedures")
    private Set<Subdivision> subdivisions = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "procedure")
    private Set<Booking> bookings = new HashSet<>();
}
