package org.workflow.Tasks.Task;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.parser.Priority;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.workflow.Tasks.Priority.PriorityRepository;
import org.workflow.Tasks.Project.ProjectEntity;
import org.workflow.Tasks.Project.ProjectRepository;
import org.workflow.Tasks.Project.ProjectService;
import org.workflow.Tasks.Status.StatusEntity;
import org.workflow.Tasks.Status.StatusService;
import org.workflow.Tasks.Priority.PriorityEntity;
import org.workflow.Tasks.Priority.PriorityService;
import org.workflow.Tasks.Tag.TagEntity;
import org.workflow.Tasks.Tag.TagRepository;
import org.workflow.Tasks.Tag.TagService;
import org.workflow.Tasks.Tag.TagWithSelectedKeyDTO;
import org.workflow.User.UserEntity;
import org.workflow.User.UserService;
import org.workflow.Utils.TaskUtility;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public Page<TaskEntity> getTaskListByProjectId(UUID projectId, Pageable pageable) {

        if (projectId == null)
            throw new RuntimeException("Project Id is required");

        Page<TaskEntity> taskList = taskRepository.findAllByProjectId(projectId, pageable);

        return taskList;
    }

    public Page<TaskEntity> getTaskListByProjectSlug(String projectSlug, Pageable pageable) {

        ProjectEntity projectDetail = projectService.getProjectBySlug(projectSlug);
        if (projectDetail == null)
            throw new RuntimeException("TaskId doesn't Exists");

        UUID projectId = projectDetail.getProjectId();
        Page<TaskEntity> taskList = this.getTaskListByProjectId(projectId, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return taskList;
    }

    public Page<TaskDTO> getTaskList(Pageable pageable) {
        Page<TaskEntity> taskList = taskRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()));

        return new PageImpl<>(taskList.getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()),
                pageable,
                taskList.getTotalElements());
    }

    public TaskEntity getTaskByTaskId(Long taskId, String projectSlug) {
        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);
        if (currentProject == null)
            return null;

        return taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());
    }

    public TaskEntity getTaskByTaskId(Long id) {
        return taskRepository.findByTaskId(id);
    }

    public List<TagWithSelectedKeyDTO> getTagListWithSelectedTags(TaskEntity currentTask) {
        List<TagEntity> tagList = tagService.getTagListByProjectId(currentTask.getProjectId());
        List<TagWithSelectedKeyDTO> tagListWithSelectedTags = tagService.tagWithSelectedKeyList(tagList,
                currentTask.getTags().stream().toList());
        return tagListWithSelectedTags;
    }

    public <T> List<T> getFilteredTagListWithSelectedTags(List<T> list, String searchQuery,
            Function<T, String> getFieldFunction) {
        return list.stream()
                .filter(item -> getFieldFunction.apply(item).toLowerCase()
                        .startsWith(searchQuery.toLowerCase()))
                .toList();
    }

    public TaskDetailsDTO createTask(TaskCreateRequestDTO updates) {
        if (updates.getProjectId() == null)
            throw new RuntimeException("project is required");

        ProjectEntity currentProject = projectService.getProjectById(updates.getProjectId());

        if (currentProject == null)
            throw new RuntimeException("project is required");

        Long totalTasksInCurrentProject = currentProject.getTotalTaskCount();
        Long newTaskId = totalTasksInCurrentProject + 1;

        TaskEntity task = new TaskEntity();
        task.setProjectId(updates.getProjectId());
        task.setTaskId(newTaskId);

        if (updates.getTitle() == null)
            throw new RuntimeException("Title is Required");
        task.setTitle(updates.getTitle());

        if (updates.getStatus() == null) {
            StatusEntity backlog = statusService.getStatusByTitle("backlog");
            task.setStatus(backlog);
        } else {
            StatusEntity status = statusService.getStatusById(updates.getStatus());
            task.setStatus(status);
        }

        if (updates.getPriority() == null) {
            PriorityEntity noPriority = priorityService.getPriorityByTitle("No Priority");
            task.setPriority(noPriority);
        } else {
            PriorityEntity priority = priorityService.getPriorityById(updates.getPriority());
            task.setPriority(priority);
        }

        if (updates.getDescription() != null) {
            task.setDescription(updates.getDescription());
        }

        if (updates.getEstimates() != null) {
            task.setEstimates(updates.getEstimates());
        }

        if (updates.getTags() != null) {
            List<TagEntity> tags = tagService.getTagListByTagIdList(updates.getTags());
            Set<TagEntity> tagList = new HashSet<>(tags);
            task.setTags(tagList);
        }

        TaskEntity savedTask = taskRepository.save(task);
        TaskDetailsDTO savedTaskDTO = modelMapper.map(savedTask, TaskDetailsDTO.class);

        currentProject.setTotalTaskCount(savedTaskDTO.taskId);
        projectRepository.save(modelMapper.map(currentProject, ProjectEntity.class));

        return savedTaskDTO;
    }

    public TaskDetailsDTO updateTaskByTaskId(String taskSlug, TaskCreateRequestDTO updates) {
        Long taskId = TaskUtility.getIdFromTaskId(taskSlug);
        String projectSlug = TaskUtility.getProjectSlugFromTaskSlug(taskSlug);

        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);

        if (currentProject == null)
            throw new RuntimeException("Task not found with id: " + taskId);

        TaskEntity existingTask = taskRepository.findByTaskId(taskId);
        taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        if (updates.getTitle() != null) {
            existingTask.setTitle(updates.getTitle());
        }

        if (updates.getStatus() != null) {
            StatusEntity status = statusService.getStatusById(updates.getStatus());
            existingTask.setStatus(status);
        }

        if (updates.getPriority() != null) {
            PriorityEntity priority = priorityService.getPriorityById(updates.getPriority());
            existingTask.setPriority(priority);
        }

        if (updates.getTags() != null) {
            existingTask.setEstimates(updates.getEstimates());
        }

        TaskEntity updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskDetailsDTO.class);
    }

    public TaskEntity patchTask(String taskSlug, TaskCreateRequestDTO updates) {
        Long taskId = TaskUtility.getIdFromTaskId(taskSlug);
        String projectSlug = TaskUtility.getProjectSlugFromTaskSlug(taskSlug);

        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);

        if (currentProject == null)
            throw new RuntimeException("Task not found with id: " + taskId);

        TaskEntity existingTask = taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        if (updates.getTitle() != null) {
            existingTask.setTitle(updates.getTitle());
        }

        if (updates.getStatus() != null) {
            StatusEntity status = statusService.getStatusById(updates.getStatus());
            existingTask.setStatus(status);
        }

        if (updates.getPriority() != null) {
            PriorityEntity priority = priorityService.getPriorityById(updates.getPriority());
            existingTask.setPriority(priority);
        }

        if (updates.getEstimates() != null) {
            existingTask.setEstimates(updates.getEstimates());
        }

        if (updates.getTags() != null) {
            Set<TagEntity> tagSet = this.updateTaskTags(updates, existingTask);
            existingTask.setTags(tagSet);
        }

        return taskRepository.save(existingTask);
    }

    public TaskDetailsDTO patchTaskTags(String taskSlug, TaskCreateRequestDTO updates) {
        Long taskId = TaskUtility.parseTaskSlug(taskSlug).taskId();
        String projectSlug = TaskUtility.parseTaskSlug(taskSlug).projectSlug();

        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);

        if (currentProject == null)
            throw new RuntimeException("Task not found with id: " + taskId);

        TaskEntity existingTask = taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        Set<TagEntity> tagSet = new HashSet<TagEntity>();

        if (updates.getTags() != null) {
            tagSet = this.updateTaskTags(updates, existingTask);
        }
        existingTask.setTags(tagSet);

        TaskEntity updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskDetailsDTO.class);
    }

    public List<TagEntity> addTaskTag(TagEntity tag, String taskSlug) {
        Long taskId = TaskUtility.parseTaskSlug(taskSlug).taskId();
        String projectSlug = TaskUtility.parseTaskSlug(taskSlug).projectSlug();

        TaskEntity existingTask = this.getTaskByTaskId(taskId, projectSlug);
        Set<TagEntity> tagSet = existingTask.getTags();
        tagSet.add(tag);

        Set<TagEntity> tagEntitySet = tagSet.stream().map(currentTag -> {
            return modelMapper.map(currentTag, TagEntity.class);
        }).collect(Collectors.toSet());
        TaskEntity existingTaskEntity = modelMapper.map(existingTask, TaskEntity.class);
        existingTaskEntity.setTags(tagEntitySet);

        TaskEntity savedTask = taskRepository.save(existingTaskEntity);
        List<TagEntity> tagList = savedTask.getTags().stream().toList();

        return tagList;
    }

    private Set<TagEntity> updateTaskTags(TaskCreateRequestDTO updates, TaskEntity existingTask) {
        Set<TagEntity> tagSet = new HashSet<>();

        for (UUID tagId : updates.getTags()) {
            TagEntity tag = tagRepository.findById(tagId).orElse(null);
            if (tag != null) {
                tagSet.add(tag);
            }
        }
        return tagSet;
    }

    public TaskDetailsDTO patchTaskStatus(String taskSlug, TaskCreateRequestDTO updates) {
        Long taskId = TaskUtility.parseTaskSlug(taskSlug).taskId();
        String projectSlug = TaskUtility.parseTaskSlug(taskSlug).projectSlug();

        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);

        if (currentProject == null)
            throw new RuntimeException("Task not found with id: " + taskId);

        TaskEntity existingTask = taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        if (updates.getStatus() == null)
            throw new RuntimeException("Missing required data");

        StatusEntity status = statusService.getStatusById(updates.getStatus());
        existingTask.setStatus(status);

        TaskEntity updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskDetailsDTO.class);
    }

    public TaskEntity patchTaskPriority(String taskSlug, TaskCreateRequestDTO updates) {
        Long taskId = TaskUtility.parseTaskSlug(taskSlug).taskId();
        String projectSlug = TaskUtility.parseTaskSlug(taskSlug).projectSlug();

        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);

        if (currentProject == null)
            throw new RuntimeException("Task not found with id: " + taskId);

        TaskEntity existingTask = taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        PriorityEntity priority = null;
        if (updates.getPriority() != null) {
            priority = priorityService.getPriorityById(updates.getPriority());
        }

        existingTask.setPriority(priority);

        TaskEntity updatedTask = taskRepository.save(existingTask);
        return updatedTask;
    }

    public TaskEntity patchTaskOrder(String taskSlug, TaskCreateRequestDTO updates) {
        Long taskId = TaskUtility.parseTaskSlug(taskSlug).taskId();
        String projectSlug = TaskUtility.parseTaskSlug(taskSlug).projectSlug();

        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);

        if (currentProject == null)
            throw new RuntimeException("Project not found with id: " + projectSlug);

        TaskEntity existingTask = taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        Long oldTaskOrder = existingTask.getTaskOrder();
        Long newTaskOrder = updates.getTaskOrder();

        if (newTaskOrder == null)
            return null;

        Long minTaskOrder = Math.min(oldTaskOrder, newTaskOrder);
        Long maxTaskOrder = Math.max(oldTaskOrder, newTaskOrder);

        List<TaskEntity> taskEntities = taskRepository.findTasksByTaskOrderBetweenAndProjectIdExcludingCurrentTask(
                minTaskOrder,
                maxTaskOrder, currentProject.getProjectId(), existingTask.getId());

        existingTask.setTaskOrder(updates.getTaskOrder());

        TaskEntity updatedTask = taskRepository.save(existingTask);

        boolean isDraggedUp = oldTaskOrder > newTaskOrder;

        Long count = isDraggedUp ? 1L : -1L;

        for (TaskEntity task : taskEntities) {
            task.setTaskOrder(task.getTaskOrder() + count);
            taskRepository.save(task);
        }

        return updatedTask;
    }

    public TaskEntity patchTaskAssignedTo(String taskSlug, TaskCreateRequestDTO
        updates) {
        Long taskId = TaskUtility.parseTaskSlug(taskSlug).taskId();
        String projectSlug = TaskUtility.parseTaskSlug(taskSlug).projectSlug();

        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);

        if (currentProject == null)
            throw new RuntimeException("Task not found with id: " + taskId);

        TaskEntity existingTask = taskRepository.findByTaskIdAndProjectId(taskId, currentProject.getProjectId());

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        UserEntity user = null;
        if (updates.getAssignedTo() != null) {
        }

        existingTask.setAssignedTo(user);

        TaskEntity updatedTask = taskRepository.save(existingTask);
        return updatedTask;
    }

    private TaskDTO convertToDTO(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDTO.class);
    }
}
