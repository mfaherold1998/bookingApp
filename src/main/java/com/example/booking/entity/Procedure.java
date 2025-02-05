package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Setter
@Getter
@Builder
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

    /*@JsonBackReference
    @ManyToMany(mappedBy = "procedures")
    private Set<Employee> employees = new HashSet<>();*/

    /*@JsonBackReference
    @ManyToMany(mappedBy = "procedures")
    private Set<Subdivision> subdivisions = new HashSet<>();*/

    /*@JsonManagedReference
    @OneToMany(mappedBy = "procedure")
    private Set<Booking> bookings = new HashSet<>();*/
}
