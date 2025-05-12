package org.workflow.Tasks.Project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, UUID>, PagingAndSortingRepository<ProjectEntity, UUID> {
    Optional<ProjectEntity> findBySlug(String slug);
}
