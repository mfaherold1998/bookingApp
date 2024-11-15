package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "Subdivisions")

public class Subdivision extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String adress;

    @Column(name = "contact_info")
    private String email;

    @OneToMany(mappedBy = "subdivision")
    private List<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "corporation_id")
    private Corporation corporation;
}
