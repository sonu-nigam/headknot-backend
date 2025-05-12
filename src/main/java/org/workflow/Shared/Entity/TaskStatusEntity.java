package org.workflow.Shared.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.workflow.Shared.Enum.TaskStatusEnum;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_status_bkp")
public class TaskStatusEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    public UUID taskstatusid;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private TaskStatusEnum status;
}