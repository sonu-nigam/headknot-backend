package com.example.headknot.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping
    public Iterable<DocumentEntity> getAllDocuments() {
        Iterable<DocumentEntity> documentList = documentService.getDocumentList();

        if (documentList == null)
            throw new RuntimeException("Document Not Found");

        return documentList;
    }

    @PostMapping
    public ResponseEntity<Void> createDocument(@RequestBody DocumentCreateRequestDTO payload, UriComponentsBuilder ucb) {
        DocumentEntity document = documentService.createDocument(payload);

        URI locationOfNewDocument = ucb
                .path("/document/{documentId}")
                .buildAndExpand(document)
                .toUri();
        return ResponseEntity.created(locationOfNewDocument).build();
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentEntity> getDocumentById(@PathVariable("documentId") UUID documentId) {
        DocumentEntity document = documentService.getDocumentById(documentId);
        if (document != null) {
            return ResponseEntity.ok(document);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{documentId}")
    public ResponseEntity<Void> updateDocument(@PathVariable("documentId") UUID documentId,
            @RequestBody DocumentCreateRequestDTO payload) {
        DocumentEntity document = documentService.getDocumentById(documentId);

        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        documentService.patchDocument(document, payload);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable("documentId") UUID documentId) {
        DocumentEntity document = documentService.getDocumentById(documentId);

        if (document != null) {
            documentService.deleteDocument(documentId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tree")
    public List<DocumentNode> getDocumentTree() {
        return documentService.getDocumentTree();
    }

//    @GetMapping("/tree/{id}")
//    public ResponseEntity<DocumentEntity> getDocumentTreeById(@PathVariable UUID id) {
//        DocumentEntity document = documentService.getDocumentTreeById(id);
//        return document != null ? ResponseEntity.ok(document) : ResponseEntity.notFound().build();
//    }
}
