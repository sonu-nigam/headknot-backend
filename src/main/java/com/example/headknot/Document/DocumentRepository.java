package com.example.headknot.Document;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository
        extends CrudRepository<DocumentEntity, UUID>, PagingAndSortingRepository<DocumentEntity, UUID> {
    List<DocumentEntity> findByParentId(UUID parentId);

    List<DocumentEntity> findByParentIdIsNull();
}
