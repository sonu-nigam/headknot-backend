package com.example.headknot.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping
//    private String getUserList(
//            Model model,
//            @RequestParam("task") String taskSlug,
//            @RequestParam(name = "search", defaultValue = "") String searchQuery) {
//
//        model.addAttribute("searchQuery", searchQuery);
//
//        return "pages/tasks/task/details/properties/assignedTo";
//    }
//
//    @GetMapping
//    private String getUserByEmail(
//        Model model,
//        @RequestParam("task") String taskSlug,
//        @RequestParam(name = "search", defaultValue = "") String searchQuery) {
//
//        model.addAttribute("searchQuery", searchQuery);
//
//        return "pages/tasks/task/details/properties/assignedTo";
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") UUID userId) {
        UserDTO user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
