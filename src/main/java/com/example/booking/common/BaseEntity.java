package com.example.booking.common;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "last_update_at")
    protected LocalDateTime updatedDate;

    @Column(name = "deleted", nullable = false)
    protected Boolean deleted = Boolean.FALSE;

}
