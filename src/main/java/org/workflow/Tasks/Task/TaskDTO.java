package org.workflow.Tasks.Task;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import org.workflow.Tasks.Priority.PriorityDTO;
import org.workflow.Tasks.Status.StatusEntity;
import org.workflow.Tasks.Tag.TagDTO;
import org.workflow.User.UserEntity;

@Data
public class TaskDTO {
    public UUID id;
    public Long taskId;
    public String title;
    public UUID projectId;
    public String description;
    public StatusEntity status;
    public Long estimates;
    public Set<TagDTO> tags;
    public PriorityDTO priority;
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;
    private UserEntity assignedTo;
}
