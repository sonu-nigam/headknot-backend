package org.workflow.Tasks.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import org.workflow.Shared.Enum.RootMenuEnum;
import org.workflow.Shared.Exception.NotFoundException;
import org.workflow.Shared.Record.NavLinkRecord;
import org.workflow.Shared.Utils.RootMenuUtils;
import org.workflow.Tasks.Priority.PriorityDTO;
import org.workflow.Tasks.Priority.PriorityService;
import org.workflow.Tasks.Project.*;
import org.workflow.Tasks.Status.StatusEntity;
import org.workflow.Tasks.Status.StatusService;
import org.workflow.Tasks.Tag.TagEntity;
import org.workflow.Tasks.Tag.TagService;
import org.workflow.Tasks.Task.TaskEntity;
import org.workflow.Tasks.Task.TaskService;
import org.workflow.User.UserEntity;
import org.workflow.User.UserService;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String ProjectList(Model model, Pageable pageable) {
        Page<ProjectEntity> projectList = projectService.getProjectList(pageable);

        if (projectList == null) {
            throw new NotFoundException("Project not found");
        }

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);

        model.addAttribute("projectList", projectList);
        return "pages/tasks/project/list/projectPage";
    }

    @GetMapping("/{slug}")
    public String ProjectBySlug(
            Model model,
            @PathVariable("slug") String slug,
            Pageable pageable,
            @RequestHeader(value = "x-refresh", defaultValue = "false") String xRefresh) {
        ProjectEntity project = projectService.getProjectBySlug(slug);
        model.addAttribute("project", project);

        Page<ProjectEntity> projectList = projectService.getProjectList(pageable);
        model.addAttribute("projectList", projectList.getContent());

        List<StatusEntity> statusList = statusService.getStatusList();
        model.addAttribute("statusList", statusList);

        List<PriorityDTO> priorityList = priorityService.getPriorityList();
        model.addAttribute("priorityList", priorityList);

        List<TagEntity> tagList = tagService.getTagListByProjectId(project.getProjectId());
        model.addAttribute("tagList", tagList);

        Sort sort = Sort.by("taskOrder").ascending();
        Page<TaskEntity> taskList = taskService.getTaskListByProjectId(project.getProjectId(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        model.addAttribute("taskList", taskList);

        String currentUrl = "/projects/" + slug;
        List<NavLinkRecord> projectHeaderLinks = ProjectUtils.getProjectNavlinks(slug, currentUrl);
        model.addAttribute("projectHeaderLinks", projectHeaderLinks);

        if (xRefresh.equals("true"))
            return "pages/tasks/project/details/taskList";

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);

        return "pages/tasks/project/details/projectPage";
    }

    @PostMapping
    public String createProject(@ModelAttribute ProjectCreateRequestDTO payload, Model model) {
        ProjectEntity createdProject = projectService.createProject(payload);
        model.addAttribute("project", createdProject);
        return "pages/tasks/project/list/projectListItem";
    }

    @PatchMapping("/{slug}")
    public ResponseEntity<Void> updateProject(@PathVariable("slug") String slug,
            @RequestBody ProjectCreateRequestDTO updates) {
        ProjectEntity updatedProject = projectService.updateProject(slug, updates);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{slug}/status")
    public String getStatusList(@PathVariable("slug") String slug, Model model) {

        List<StatusEntity> statusList = statusService.getStatusList();

        model.addAttribute("projectSlug", slug);
        model.addAttribute("statusList", statusList);
        return "pages/tasks/project/overview/statusList/statusList";
    }

    @PatchMapping("/{slug}/status")
    public ResponseEntity<Void> updateStatusList(@PathVariable("slug") String slug,
            @ModelAttribute ProjectCreateRequestDTO updates, UriComponentsBuilder ucb) {

        try {

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("HX-Trigger", "refreshProjectStatusList");
            return ResponseEntity.noContent().headers(responseHeaders).build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
