package org.workflow.App;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class AppController {

    @GetMapping
    public String RootPage() {
        return "common/underConstruction";
    }

}
