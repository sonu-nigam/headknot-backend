package org.workflow.Tasks.Tag;

import jakarta.persistence.*;
import lombok.*;
import org.workflow.Shared.Entity.BaseEntity;
import org.workflow.Tasks.Task.TaskEntity;

import java.util.Set;
import java.util.UUID;

@Table(name = "task_tag")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    public UUID tagId;

    // @Column(nullable = false)
    private String title;

    @ManyToMany(mappedBy = "tags")
    private Set<TaskEntity> tasks;

    private UUID projectId;

    private String color;

    private String icon;

}
