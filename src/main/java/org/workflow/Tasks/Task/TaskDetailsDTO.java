package org.workflow.Tasks.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import org.workflow.Tasks.Status.StatusEntity;
import org.workflow.Tasks.Tag.TagDTO;
import org.workflow.Tasks.Priority.PriorityDTO;
import org.workflow.User.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDetailsDTO {
    public UUID id;
    public Long taskId;
    public String title;
    public UUID projectId;
    public String description;
    public Long estimates;
    public StatusEntity status;
    public PriorityDTO priority;
    public Set<TagDTO> tags;
    // public String attachments;

     public UserEntity assignedTo;
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
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;
    // public String deletedAt;
    // public String completedAt;
    // public String startedAt;
    // public String dueDateAt;
    // public String assignedAt;
}
