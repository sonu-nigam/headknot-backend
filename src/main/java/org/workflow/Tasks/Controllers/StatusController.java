package org.workflow.Tasks.Controllers;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.workflow.Tasks.Status.StatusDTO;
import org.workflow.Tasks.Status.StatusService;

@Controller
@RequestMapping(value = "/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @PatchMapping("/list")
    private ResponseEntity<Void> updateStatusList(@RequestBody StatusDTO statusDTO) {
        return ResponseEntity.ok(null);
    }
}
