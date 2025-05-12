package org.workflow.Tasks.Tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagRepository extends CrudRepository<TagEntity, UUID>, PagingAndSortingRepository<TagEntity, UUID> {
    List<TagEntity> findAllByProjectId(UUID projectId);
    List<TagEntity> findAllByTagIdIn(Set<UUID> tagIdList);
}
