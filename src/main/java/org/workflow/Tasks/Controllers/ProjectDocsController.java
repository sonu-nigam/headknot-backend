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
import org.workflow.Shared.Utils.RootMenuUtils;
import org.workflow.Shared.Record.NavLinkRecord;
import org.workflow.Tasks.Project.ProjectEntity;
import org.workflow.Tasks.Project.ProjectService;
import org.workflow.Tasks.Project.ProjectUtils;

@Controller
@RequestMapping(value = "/projects/{projectSlug}/docs")
public class ProjectDocsController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String ProjectList(@PathVariable("projectSlug") String projectSlug, Model model, Pageable pageable) {

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);

        ProjectEntity project = projectService.getProjectBySlug(projectSlug);
        model.addAttribute("project", project);

        String currentUrl = "/projects/" + projectSlug + "/docs";
        List<NavLinkRecord> projectHeaderLinks = ProjectUtils.getProjectNavlinks(projectSlug, currentUrl);
        model.addAttribute("projectHeaderLinks", projectHeaderLinks);

        return "pages/tasks/project/settings/settingsPage";
    }
}
