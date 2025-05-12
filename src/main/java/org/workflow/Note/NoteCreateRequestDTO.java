package org.workflow.Note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteCreateRequestDTO {
    private UUID noteId;
    private String title;
    private String description;
}
