package org.workflow.Tasks.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateRequestDTO {
    public UUID tagId;
    public UUID projectId;
    public String title;
    public String color;
    public String icon;
}
