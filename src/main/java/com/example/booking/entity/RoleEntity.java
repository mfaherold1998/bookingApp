package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;


@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Roles")
@SQLRestriction("deleted = false")
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();*/

    private String name;
}
