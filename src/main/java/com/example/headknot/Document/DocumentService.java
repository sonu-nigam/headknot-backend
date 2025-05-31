package com.example.headknot.Document;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Iterable<DocumentEntity> getDocumentList() {
        Iterable<DocumentEntity> noteList = documentRepository.findAll();

        return noteList;
    }

    public DocumentEntity getDocumentById(UUID documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }

    public DocumentEntity createDocument(DocumentCreateRequestDTO payload) {
        DocumentEntity document = new DocumentEntity();
        DocumentEntity parentDocument = null;
        if (payload.getParent() != null) {
            parentDocument = getDocumentById(payload.getParent());
        }
        if (parentDocument != null)
            document.setParent(parentDocument);

        if (payload.getTitle() != null) {
            document.setTitle(payload.getTitle());
        }
        return documentRepository.save(document);
    }

    public DocumentEntity patchDocument(DocumentEntity existingDocument, DocumentCreateRequestDTO payload) {
        if (payload.getTitle() != null) {
            existingDocument.setTitle(payload.getTitle());
        }
        if (payload.getDescription() != null && !payload.getDescription().isEmpty()) {
            existingDocument.setDescription(payload.getDescription());
        }
        return documentRepository.save(existingDocument);
    }

    public void deleteDocument(UUID documentId) {
        documentRepository.deleteById(documentId);
    }

    public List<DocumentNode> getDocumentTree() {
        List<DocumentEntity> rootDocuments = documentRepository.findByParentIdIsNull();
        List<DocumentNode> rootDocumentNodes = modelMapper.map(rootDocuments, new TypeToken<List<DocumentNode>>() {}.getType());
        rootDocumentNodes.forEach(this::buildTree);
        return rootDocumentNodes;
    }

    private void buildTree(DocumentNode document) {
        List<DocumentEntity> children = documentRepository.findByParentId(document.getId());
        if (children == null) return;
        children.forEach((child) -> {
            DocumentNode childDocument = modelMapper.map(child, DocumentNode.class);
            document.getChildren().add(childDocument);
            this.buildTree(childDocument);
        });
    }

    public DocumentNode getDocumentTreeById(UUID id) {
        return documentRepository.findById(id)
            .map(document -> {
                // Initialize children if needed
                DocumentNode childDocument = modelMapper.map(document, DocumentNode.class);
                buildTree(childDocument);
                return childDocument;
            })
            .orElse(null);
    }}
