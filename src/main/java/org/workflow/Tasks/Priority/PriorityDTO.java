package org.workflow.Tasks.Priority;

import lombok.Data;

import java.util.UUID;

@Data
public class PriorityDTO {
    private UUID priorityId;
    private String title;
    private Long precedence;
    private String color;
}
