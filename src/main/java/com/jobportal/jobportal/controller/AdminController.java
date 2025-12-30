package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.service.JobService;
import com.jobportal.jobportal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final JobService jobService;

    public AdminController(UserService userService, JobService jobService) {
        this.userService = userService;
        this.jobService = jobService;
    }


    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-users";
    }

    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        return "admin-jobs";
    }

    @PostMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/admin/jobs";
    }

    @PostMapping("/users/disable/{id}")
    public String disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return "redirect:/admin/users";
    }



}
