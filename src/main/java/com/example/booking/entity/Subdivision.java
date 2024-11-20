package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Subdivisions")
@SQLRestriction("deleted = false")
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
    private Set<Employee> employees = new HashSet<>();;

    @ManyToOne
    @JoinColumn(name = "corporation_id")
    private Corporation corporation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "subdivision_procedure", joinColumns = @JoinColumn(name = "subdivision_id"), inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private Set<Procedure> procedures = new HashSet<>();
}
