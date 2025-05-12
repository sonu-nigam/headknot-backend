package org.workflow.Tasks.Status;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository
        extends CrudRepository<StatusEntity, UUID>, PagingAndSortingRepository<StatusEntity, UUID> {
    Optional<StatusEntity> findByStatusName(String statusName);
}
