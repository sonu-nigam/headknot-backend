package org.workflow.Shared.Entity;

import jakarta.persistence.*;
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
