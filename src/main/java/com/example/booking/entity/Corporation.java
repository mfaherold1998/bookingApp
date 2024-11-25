package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Table(name = "Corporations")
@SQLRestriction("deleted = false")
public class Corporation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "corporation_proprietor", joinColumns = @JoinColumn(name = "corporation_id"), inverseJoinColumns = @JoinColumn(name = "proprietor_id"))
    private Set<Proprietor> proprietors = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "corporation")
    private Set<Subdivision> subdivisions = new HashSet<>();


}
