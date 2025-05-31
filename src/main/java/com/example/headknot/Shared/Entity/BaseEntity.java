package com.example.headknot.Shared.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected OffsetDateTime createdAt;

    @Column(updatable = false)
    protected OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
