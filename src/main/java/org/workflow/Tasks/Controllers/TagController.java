package org.workflow.Tasks.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.workflow.Tasks.Tag.TagDTO;
import org.workflow.Tasks.Tag.TagService;

@Controller
@RequestMapping(value = "/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping
    private String createTag(@ModelAttribute TagDTO tagDTO, Model model) {
        // ProjectDTO createdTag = tagService.createProject(tagDTO);
        // model.addAttribute("tag", createdTag);
        return "pages/tasks/project/list/projectListItem";
    }

}
