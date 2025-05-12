package org.workflow.Note;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.UUID;

public interface NoteRepository extends CrudRepository<NoteEntity, UUID>, PagingAndSortingRepository<NoteEntity, UUID> {
    NoteEntity findByNoteId(UUID pageId);
}
