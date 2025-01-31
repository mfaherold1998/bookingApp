package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Builder
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

    /*@JsonBackReference
    @OneToMany(mappedBy = "subdivision")
    private Set<Employee> employees = new HashSet<>();*/

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "corporation_id")
    private Corporation corporation;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "subdivision_procedure", joinColumns = @JoinColumn(name = "subdivision_id"), inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private Set<Procedure> procedures = new HashSet<>();
}
