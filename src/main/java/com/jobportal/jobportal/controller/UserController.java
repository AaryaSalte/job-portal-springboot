package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.JobApplication;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.service.JobApplicationService;
import com.jobportal.jobportal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final JobApplicationService jobApplicationService;

    public UserController(UserService userService, JobApplicationService jobApplicationService) {
        this.userService = userService;
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping("/register")
    public String showRegisterPage(
            @RequestParam(value = "success", required = false) String success,
            Model model) {

        model.addAttribute("user", new User());

        if (success != null) {
            model.addAttribute("message", "User registered successfully!");
        }

        return "register";
    }


    @PostMapping("/register")
    public String registerUser(User user) {
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/user/applications")
    public String viewUserApplications(Authentication authentication, Model model) {

        User user = userService.findByEmail(authentication.getName());
        List<JobApplication> applications =
                jobApplicationService.getApplicationsForUser(user);

        model.addAttribute("applications", applications);
        return "user-applications";
    }


}
