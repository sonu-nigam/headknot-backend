package org.workflow.Tasks.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequestDTO {
    private UUID projectId;
    private String projectName;
    private String slug;
    private String description;
    private Set<UUID> priority;
    private Set<UUID> status;
}
