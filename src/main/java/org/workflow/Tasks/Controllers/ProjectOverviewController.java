package org.workflow.Tasks.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.workflow.Shared.Enum.RootMenuEnum;
import org.workflow.Shared.Exception.NotFoundException;
import org.workflow.Shared.Utils.RootMenuUtils;
import org.workflow.Shared.Record.NavLinkRecord;
import org.workflow.Tasks.Priority.PriorityDTO;
import org.workflow.Tasks.Priority.PriorityService;
import org.workflow.Tasks.Project.ProjectEntity;
import org.workflow.Tasks.Project.ProjectService;
import org.workflow.Tasks.Status.StatusDTO;
import org.workflow.Tasks.Status.StatusEntity;
import org.workflow.Tasks.Status.StatusService;
import org.workflow.Tasks.Tag.TagDTO;
import org.workflow.Tasks.Tag.TagEntity;
import org.workflow.Tasks.Tag.TagService;
import org.workflow.Tasks.Task.TaskService;

import org.workflow.Tasks.Project.ProjectUtils;

import java.util.List;

@Controller
@RequestMapping(value = "/projects/{projectSlug}/overview")
public class ProjectOverviewController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TagService tagService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private PriorityService priorityService;

    @GetMapping
    public String ProjectList(@PathVariable("projectSlug") String projectSlug, Model model, Pageable pageable) {

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);

        ProjectEntity project = projectService.getProjectBySlug(projectSlug);
        model.addAttribute("project", project);

        String currentUrl = "/projects/" + projectSlug + "/overview";
        List<NavLinkRecord> projectHeaderLinks = ProjectUtils.getProjectNavlinks(projectSlug, currentUrl);
        model.addAttribute("projectHeaderLinks", projectHeaderLinks);

        List<TagEntity> labelList = tagService.getTagListByProjectId(project.getProjectId());
        model.addAttribute("labelList", labelList);

        List<StatusEntity> statusList = statusService.getStatusList();
        model.addAttribute("statusList", statusList);

        List<PriorityDTO> priorityList = priorityService.getPriorityList();
        model.addAttribute("priorityList", priorityList);

        return "pages/tasks/project/overview/overviewPage";
    }
}
