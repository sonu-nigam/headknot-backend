package org.workflow.Note;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.TreeNode;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Page<NoteEntity> getNoteList(Pageable pageable) {
        Page<NoteEntity> noteList = noteRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()));

        return noteList;
    }

    public Iterable<NoteEntity> buildFileTree() {
        Iterable<NoteEntity> noteList = noteRepository.findAll();
        return noteList;
    }

    public NoteEntity getNoteByNoteId(UUID noteId) {
        return noteRepository.findByNoteId(noteId);
    }

    public class TreeNode<T> {
        private String id;
        private TreeNode<T> parent;
        private T data;
        private List<TreeNode<T>> children;

        public TreeNode(String id, T data) {
            this.id = id;
            this.data = data;
            this.children = new ArrayList<>();
        }

        public String getId() {
            return this.id;
        }

        public TreeNode<T> getParent() {
            return this.parent;
        }

        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        public List<TreeNode<T>> getChildren() {
            return this.children;
        }

        public void addChildren(TreeNode<T> child) {
            children.add(child);
        }
    }

    private static <T> String getParentId(TreeNode<T> node) {
        return node.getParent() != null ? node.getParent().getId() : null;
    }

    // Helper method to find a node by ID
    private static <T> TreeNode<T> nodeMap(List<TreeNode<T>> nodes, String id) {
        for (TreeNode<T> node : nodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
}
