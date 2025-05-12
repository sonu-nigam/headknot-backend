package org.workflow.Document;

import jakarta.persistence.*;
import lombok.*;
import org.workflow.Shared.Entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;
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
