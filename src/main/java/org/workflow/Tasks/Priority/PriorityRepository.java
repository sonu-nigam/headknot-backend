package org.workflow.Tasks.Priority;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriorityRepository extends CrudRepository<PriorityEntity, UUID>, PagingAndSortingRepository<PriorityEntity, UUID> {
    Optional<PriorityEntity> findByTitle(String title);

}
