package org.workflow.Tasks.Tag;

import lombok.Data;

import java.util.UUID;

@Data
public class TagDTO {
    public UUID tagId;
    public UUID projectId;
    public String title;
    public String color;
    public String icon;
}
