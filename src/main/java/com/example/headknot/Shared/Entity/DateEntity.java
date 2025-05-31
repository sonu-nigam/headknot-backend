package com.example.headknot.Shared.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class DateEntity implements Serializable {
    @Column(updatable = false, insertable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();

    @Column(updatable = false)
    protected LocalDateTime updatedAt = LocalDateTime.now();
}
