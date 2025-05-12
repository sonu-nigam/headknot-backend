package org.workflow.Tasks.Status;

import lombok.Data;

import java.util.UUID;

@Data
public class StatusDTO {
    private UUID statusId;
    private String statusName;
    private String statusType;
    private String statusColor;
    private Long statusOrder;
}
