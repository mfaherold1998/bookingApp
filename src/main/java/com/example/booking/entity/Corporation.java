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
@SQLRestriction("deleted = false")
@Table(name = "Corporations")

public class Corporation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "corporation_proprietor", joinColumns = @JoinColumn(name = "corporation_id"), inverseJoinColumns = @JoinColumn(name = "proprietor_id"))
    private List<Proprietor> proprietors = Collections.emptyList();

    @OneToMany(mappedBy = "corporation")
    private List<Subdivision> subdivisions = Collections.emptyList();


}
