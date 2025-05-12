package org.workflow.Tasks.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateRequestDTO {
    private UUID id;
    private Long taskId;
    private String title;
    private UUID projectId;
    private String description;
    private Long estimates;
    private UUID status;
    private UUID priority;
    private Set<UUID> tags;
    private UUID assignedTo;
    private Long taskOrder;
}
