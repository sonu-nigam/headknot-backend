package org.workflow.Tasks.Priority;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.workflow.Shared.Entity.BaseEntity;
import org.workflow.Tasks.Task.TaskEntity;

import java.util.Set;
import java.util.UUID;

@Table(name = "task_priority")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriorityEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID priorityId;

    @Column(nullable = false)
    private String title;

    @Column(name = "color")
    private String color;

    @Column(nullable = false)
    private Long precedence;

    @OneToMany(mappedBy = "priority")
    private Set<TaskEntity> tasks;
}
