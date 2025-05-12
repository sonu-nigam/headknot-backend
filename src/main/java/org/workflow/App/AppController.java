package org.workflow.App;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.workflow.Shared.Enum.RootMenuEnum;
import org.workflow.Shared.Utils.RootMenuUtils;

@Controller
@RequestMapping(value = "/")
public class AppController {

    @GetMapping
    public String RootPage(Model model, Pageable pageable) {

        List<RootMenuEnum> rootMenu = RootMenuUtils.getMenuItems();
        model.addAttribute("navMenu", rootMenu);

        return "common/underConstruction";
    }

}
