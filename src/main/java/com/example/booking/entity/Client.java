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

@Table(name = "Clients")
@SQLRestriction("deleted = false")
public class Client extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    /*@JsonManagedReference
    @OneToMany(mappedBy = "client")
    private Set<Booking> bookings = new HashSet<>();*/

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, optional = true)
    private UserEntity user;
}
