package org.workflow.Note;

import jakarta.persistence.*;
import lombok.*;
import org.workflow.Shared.Entity.BaseEntity;

import java.util.UUID;

@Table(name = "note")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// @IdClass(TaskCompositeKey.class)
public class NoteEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID noteId;

    // @Id
    private UUID parent;

    @Column(nullable = false)
    private String title;

    private String description;
}
