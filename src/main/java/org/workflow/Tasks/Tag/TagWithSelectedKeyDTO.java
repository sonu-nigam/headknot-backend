package org.workflow.Tasks.Tag;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TagWithSelectedKeyDTO extends TagDTO {
    public boolean selected;
}
