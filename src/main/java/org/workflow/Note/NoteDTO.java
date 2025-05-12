
package org.workflow.Note;

import lombok.Data;

import java.util.UUID;

@Data
public class NoteDTO {
    private UUID noteId;
    private String title;
    private String description;
}
