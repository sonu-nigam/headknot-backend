package com.example.headknot.Document;

import com.example.headknot.Shared.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "document")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private DocumentEntity parent;

    private String title;

    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition = "TEXT")
    private String description;
}
