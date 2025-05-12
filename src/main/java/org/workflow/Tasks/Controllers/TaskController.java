package org.workflow.Tasks.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import org.workflow.Shared.Enum.RootMenuEnum;
import org.workflow.Shared.Utils.RootMenuUtils;
import org.workflow.Tasks.Priority.PriorityDTO;
import org.workflow.Tasks.Priority.PriorityService;
import org.workflow.Tasks.Project.ProjectEntity;
import org.workflow.Tasks.Project.ProjectService;
import org.workflow.Tasks.Status.StatusEntity;
import org.workflow.Tasks.Status.StatusService;
import org.workflow.Tasks.Tag.*;
import org.workflow.Tasks.Task.TaskCreateRequestDTO;
import org.workflow.Tasks.Task.TaskDetailsDTO;
import org.workflow.Tasks.Task.TaskEntity;
import org.workflow.Tasks.Task.TaskService;
import org.workflow.User.UserService;
import org.workflow.User.UserEntity;
import org.workflow.Utils.TaskUtility;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String RootPage(Model model, Pageable pageable) {

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);
        model.addAttribute("projectSlug", "WMP");

        return "pages/tasks/root/homePage";
    }

    @GetMapping(value = "/{taskSlug}")
    public String taskById(
            Model model,
            @PathVariable("taskSlug") String taskSlug,
            Pageable pageable,
            @RequestHeader(value = "x-refresh", defaultValue = "false") String xRefresh) {
        String projectSlug = TaskUtility.getProjectSlugFromTaskSlug(taskSlug);
        Long taskId = TaskUtility.getIdFromTaskId(taskSlug);

        TaskEntity currentTask = taskService.getTaskByTaskId(taskId, projectSlug);
        model.addAttribute("task", currentTask);

        if (xRefresh.equals("true"))
            return "pages/tasks/project/project";

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);
        model.addAttribute("projectSlug", projectSlug);
        model.addAttribute("taskSlug", taskSlug);
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", currentTask.getProjectId());

        List<StatusEntity> statusList = statusService.getStatusList();
        model.addAttribute("statusList", statusList);

        List<PriorityDTO> priorityList = priorityService.getPriorityList();
        model.addAttribute("priorityList", priorityList);

        List<TagEntity> tagList = tagService.getTagListByProjectId(currentTask.getProjectId());
        List<TagWithSelectedKeyDTO> tagListWithSelectedTags = tagService.tagWithSelectedKeyList(tagList,
                currentTask.getTags().stream().toList());
        model.addAttribute("selectedTags", currentTask.getTags());
        model.addAttribute("tagList", tagListWithSelectedTags);

        return "pages/tasks/task/details/taskPage";
    }

    @PostMapping
    public String createTask(@ModelAttribute TaskCreateRequestDTO updates, Model model) {
        TaskDetailsDTO createdTask = taskService.createTask(updates);
        ProjectEntity currentProject = projectService.getProjectById(
                createdTask.projectId);
        model.addAttribute("task", createdTask);
        model.addAttribute("project", currentProject);
        return "pages/tasks/task/list/taskListItem";
    }

    @PatchMapping("/{taskSlug}")
    public String updateTask(
            @PathVariable("taskSlug") String taskSlug,
            @ModelAttribute TaskCreateRequestDTO updates,
            Model model) {

        TaskEntity updatedTask = taskService.patchTask(taskSlug, updates);

        if (updatedTask == null) {
            return "pages/tasks/task/details/taskPage";
        }

        model.addAttribute("task", updatedTask);

        if (updates.getTitle() != null && !updates.getTitle().isEmpty()) {
            List<StatusEntity> statusList = statusService.getStatusList();
            model.addAttribute("statusList", statusList);
            return "pages/tasks/task/details/properties/status";
        }

        if (updates.getStatus() != null) {
            List<StatusEntity> statusList = statusService.getStatusList();
            model.addAttribute("statusList", statusList);
            return "pages/tasks/task/details/properties/status";
        }

        if (updates.getPriority() != null) {
            List<PriorityDTO> priorityList = priorityService.getPriorityList();
            model.addAttribute("priorityList", priorityList);
            return "pages/tasks/task/details/properties/priority";
        }

        if (updates.getEstimates() != null) {
            return "pages/tasks/task/details/properties/estimates";
        }

        if (updates.getTags() != null) {
            List<TagEntity> tagList = tagService.getTagListByProjectId(updatedTask.getProjectId());
            List<TagWithSelectedKeyDTO> tagListWithSelectedTags = tagService.tagWithSelectedKeyList(tagList,
                    updatedTask.getTags().stream().toList());
            model.addAttribute("selectedTags", updatedTask.getTags());
            model.addAttribute("tagList", tagListWithSelectedTags);
            return "pages/tasks/task/details/properties/tag";
        }

        return "pages/tasks/task/details/taskPage";
    }

    @GetMapping("/{taskSlug}/labels")
    public String getAllTagsInProject(
            Model model,
            @PathVariable("taskSlug") String taskSlug,
            @RequestParam(name = "search", defaultValue = "") String searchQuery) {
        String projectSlug = TaskUtility.getProjectSlugFromTaskSlug(taskSlug);
        Long taskId = TaskUtility.getIdFromTaskId(taskSlug);

        TaskEntity currentTask = taskService.getTaskByTaskId(taskId, projectSlug);

        model.addAttribute("task", currentTask);

        List<TagWithSelectedKeyDTO> tagListWithSelectedTags = taskService.getTagListWithSelectedTags(currentTask);
        List<TagWithSelectedKeyDTO> filteredTagListWithSelectedTags = taskService
                .getFilteredTagListWithSelectedTags(tagListWithSelectedTags, searchQuery, TagDTO::getTitle);
        model.addAttribute("selectedTags", currentTask.getTags());
        model.addAttribute("tagList", filteredTagListWithSelectedTags);
        model.addAttribute("searchQuery", searchQuery);
        return "pages/tasks/task/details/properties/labels";
    }

    @PostMapping("/{taskSlug}/labels")
    public ResponseEntity<Void> createTaskTag(
            @PathVariable("taskSlug") String taskSlug,
            @ModelAttribute TagCreateRequestDTO tag,
            UriComponentsBuilder ucb) {

        String projectSlug = TaskUtility.parseTaskSlug(taskSlug).projectSlug();

        try {
            TagEntity newTag = tagService.createTag(tag, projectSlug);
            taskService.addTaskTag(newTag, taskSlug);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("HX-Trigger", taskSlug + ":refreshTaskLabels");
            URI locationOfNewTaskTag = ucb
                    .path("/tasks/{taskSlug}")
                    .buildAndExpand(taskSlug)
                    .toUri();
            return ResponseEntity.created(locationOfNewTaskTag).headers(responseHeaders).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PatchMapping("/{taskSlug}/labels")
    public ResponseEntity<Void> updateTaskTags(
            @PathVariable("taskSlug") String taskSlug,
            @ModelAttribute TaskCreateRequestDTO updates) {

        TaskDetailsDTO updatedTask = taskService.patchTaskTags(taskSlug, updates);

        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("HX-Trigger", taskSlug + ":refreshTaskLabels");
        return ResponseEntity.noContent().headers(responseHeaders).build();
    }

    @PatchMapping("/{taskSlug}/status")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable("taskSlug") String taskSlug,
            @ModelAttribute TaskCreateRequestDTO updates) {

        if (updates.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        TaskDetailsDTO updatedTask = taskService.patchTaskStatus(taskSlug, updates);

        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("HX-Trigger", "refreshTaskStatus");
        return ResponseEntity.noContent().headers(responseHeaders).build();
    }

    @GetMapping("/{taskSlug}/status")
    public String getTaskStatus(
            Model model,
            @PathVariable("taskSlug") String taskSlug,
            @RequestParam(name = "search", defaultValue = "") String searchQuery) {
        String projectSlug = TaskUtility.getProjectSlugFromTaskSlug(taskSlug);
        Long taskId = TaskUtility.getIdFromTaskId(taskSlug);

        TaskEntity currentTask = taskService.getTaskByTaskId(taskId, projectSlug);

        model.addAttribute("task", currentTask);

        List<StatusEntity> statusList = statusService.getStatusList();
        model.addAttribute("statusList", statusList);

        return "pages/tasks/task/details/properties/status";
    }

    @GetMapping("/{taskSlug}/priority")
    public String getTaskPriority(
            Model model,
            @PathVariable("taskSlug") String taskSlug,
            @RequestParam(name = "search", defaultValue = "") String searchQuery) {
        String projectSlug = TaskUtility.getProjectSlugFromTaskSlug(taskSlug);
        Long taskId = TaskUtility.getIdFromTaskId(taskSlug);

        TaskEntity currentTask = taskService.getTaskByTaskId(taskId, projectSlug);

        model.addAttribute("task", currentTask);

        List<PriorityDTO> priorityList = priorityService.getPriorityList();
        model.addAttribute("priorityList", priorityList);

        return "pages/tasks/task/details/properties/priority";
    }

    @PatchMapping("/{taskSlug}/priority")
    public ResponseEntity<Void> updateTaskPriority(
            @PathVariable("taskSlug") String taskSlug,
            @ModelAttribute TaskCreateRequestDTO updates) {

        TaskEntity updatedTask = taskService.patchTaskPriority(taskSlug, updates);

        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("HX-Trigger", "refreshTaskPriority");
        return ResponseEntity.noContent().headers(responseHeaders).build();
    }

    @GetMapping("/{taskSlug}/assignedTo")
    public String getTaskAssignedTo(
            Model model,
            @PathVariable("taskSlug") String taskSlug,
            @RequestParam(name = "search", defaultValue = "") String searchQuery) {
        String projectSlug = TaskUtility.getProjectSlugFromTaskSlug(taskSlug);
        Long taskId = TaskUtility.getIdFromTaskId(taskSlug);

        TaskEntity currentTask = taskService.getTaskByTaskId(taskId, projectSlug);

        model.addAttribute("task", currentTask);

        model.addAttribute("searchQuery", searchQuery);

        return "pages/tasks/task/details/properties/assignedTo";
    }

    @PatchMapping("/{taskSlug}/assignedTo")
    public ResponseEntity<Void> updateTaskAssignedTo(
            @PathVariable("taskSlug") String taskSlug,
            @ModelAttribute TaskCreateRequestDTO updates) {

        TaskEntity updatedTask = taskService.patchTaskAssignedTo(taskSlug, updates);

        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("HX-Trigger", "refresh_task_assignedTo:" + taskSlug);
        return ResponseEntity.noContent().headers(responseHeaders).build();
    }

    @PatchMapping("/{taskSlug}/taskOrder")
    @Transactional
    public ResponseEntity<Void> updateTaskOrder(
            @PathVariable("taskSlug") String taskSlug,
            @ModelAttribute TaskCreateRequestDTO updates) {

        TaskEntity updatedTask = taskService.patchTaskOrder(taskSlug, updates);

        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("HX-Trigger", "refreshTaskList");
        return ResponseEntity.noContent().headers(responseHeaders).build();
    }
}
