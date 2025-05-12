
package org.workflow.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DocumentDTO {
    private UUID id;
    private String title;
    private String description;

    public DocumentDTO(DocumentEntity document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.description = document.getDescription();
    }

    // Getters and setters
}