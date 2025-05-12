package org.workflow.Tasks.Task;

import jakarta.persistence.*;
import lombok.*;
import org.workflow.Shared.Entity.BaseEntity;
import org.workflow.Tasks.Tag.TagEntity;
import org.workflow.Tasks.Status.StatusEntity;
import org.workflow.Tasks.Priority.PriorityEntity;
import org.workflow.User.UserEntity;

import java.util.Set;
import java.util.UUID;

@Table(name = "task", uniqueConstraints = { @UniqueConstraint(columnNames = { "taskId", "projectId" }) })
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// @IdClass(TaskCompositeKey.class)
public class TaskEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    // @Id
    @Column(nullable = false)
    private Long taskId;

    // @Id
    @Column(nullable = false)
    private UUID projectId;

    @Column(nullable = false)
    private String title;

    private String description;

    private String attachments;

    private Long estimates;

    private Long taskOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private StatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority")
    private PriorityEntity priority;

    @ManyToMany
    @JoinTable(name = "TaskTags", joinColumns = @JoinColumn(name = "taskId"), inverseJoinColumns = @JoinColumn(name = "tagId"))
    private Set<TagEntity> tags;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    private UserEntity assignedTo;

    // public String assignedBy;
    // public String subscribers;
    // public String tags;
    // public String dueDate;
    // public String comments;
    // public String subTasks;
    // public String dependencies;
    // public String relatedItems;
    // public String timeTracking;
    // public String checklists;
    // public String reminders;
    // public String resolution;
    //
    // public String activity;
    // public String createdBy;
    // public String deletedAt;
    // public String completedAt;
    // public String startedAt;
    // public String dueDateAt;
    // public String assignedAt;

}
