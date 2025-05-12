package org.workflow.Tasks.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends CrudRepository<TaskEntity, UUID>, PagingAndSortingRepository<TaskEntity, UUID> {
    TaskEntity findByTaskId(Long taskId);

    TaskEntity findByTaskIdAndProjectId(Long taskId, UUID projectId);

    Page<TaskEntity> findAllByProjectId(UUID projectId, Pageable pageable);

    @Query("SELECT t FROM TaskEntity t " +
            "WHERE t.taskOrder >= :minTaskOrder AND t.taskOrder <= :maxTaskOrder " +
            "AND t.projectId = :projectId " +
            "AND t.id != :taskId")
    List<TaskEntity> findTasksByTaskOrderBetweenAndProjectIdExcludingCurrentTask(
            @Param("minTaskOrder") Long minTaskOrder,
            @Param("maxTaskOrder") Long maxTaskOrder,
            @Param("projectId") UUID projectId,
            @Param("taskId") UUID taskId);

    List<TaskEntity> findByTaskOrderBetweenAndProjectId(Long minTaskOrder, Long maxTaskOrder, UUID projectId);
}
