package org.workflow.Note;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workflow.Shared.Enum.RootMenuEnum;
import org.workflow.Shared.Utils.RootMenuUtils;

@RestController
@RequestMapping(value = "/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping
    public Page<NoteEntity> getAllNotes(Pageable pageable) {
        Page<NoteEntity> noteList = noteService.getNoteList(pageable);

        if (noteList == null)
            throw new RuntimeException("Note Not Found");

        return noteList;
    }

    @GetMapping("/filetree")
    public Iterable<NoteEntity> getFileTree() {
        Iterable<NoteEntity> noteList = noteService.buildFileTree();

        if (noteList == null)
            throw new RuntimeException("Note Not Found");

        return noteList;
    }

    @GetMapping("/{noteId}")
    public NoteEntity getNote(UUID noteId) {
        NoteEntity note = noteService.getNoteByNoteId(noteId);

        if (note == null)
            throw new RuntimeException("Note Not Found");

        return note;
    }

    @PostMapping
    public String createNote(Model model, Pageable pageable) {

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);

        return "common/underConstruction";
    }

}
