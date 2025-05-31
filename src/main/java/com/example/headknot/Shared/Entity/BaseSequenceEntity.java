package com.example.headknot.Shared.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseSequenceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, insertable = false, nullable = false)
    protected Long id;

    @Column(updatable = false, insertable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();

    @Column(updatable = false)
    protected LocalDateTime updatedAt = LocalDateTime.now();
}
