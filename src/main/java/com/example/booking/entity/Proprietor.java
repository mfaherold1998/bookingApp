package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Table(name = "Proprietors")
@SQLRestriction("deleted = false")
public class Proprietor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @JsonBackReference
    @ManyToMany(mappedBy = "proprietors")
    private Set<Corporation> corporations = new HashSet<>();

    @OneToOne(mappedBy = "proprietor", cascade = CascadeType.ALL, optional = true)
    private UserEntity user;
}
