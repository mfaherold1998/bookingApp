package com.example.booking.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {

    Optional<E> findFirstByOrderByIdDesc();
}
