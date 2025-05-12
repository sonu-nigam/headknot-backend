package org.workflow.Tasks.Status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.workflow.Shared.Entity.BaseEntity;
import org.workflow.Tasks.Task.TaskEntity;

import java.util.Set;
import java.util.UUID;

@Table(name = "task_status")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatusEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID statusId;

    @Column(nullable = false, name = "name")
    private String statusName;

    @Column(nullable = false, name = "color")
    private String statusColor;

    @Column(nullable = false, name = "status_order")
    private Long statusOrder;

    @OneToMany(mappedBy = "status")
    private Set<TaskEntity> tasks;

    // private String statusColor;
    // private String statusIcon;
    // private String statusUpdatedAt;
    // private String statusDeletedAt;
    // private String statusCreatedBy;
    // private String statusUpdatedBy;
    // private String statusDeletedBy;
    // private String statusCreatedAt;
}
