package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.service.JobApplicationService;
import com.jobportal.jobportal.service.JobService;
import com.jobportal.jobportal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jobportal.jobportal.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class JobController {

    private final JobApplicationService jobApplicationService;
    private final JobService jobService;
    private final UserService userService;


    public JobController(JobService jobService,
                         JobApplicationService jobApplicationService,
                         UserService userService) {
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
        this.userService = userService;
    }



    @GetMapping("/jobs")
    public String viewJobs(
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("jobs", jobService.searchJobs(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("jobs", jobService.getAllJobs());
        }

        return "jobs";
    }




    @PostMapping("/jobs/apply/{id}")
    public String applyJob(@PathVariable Long id,
                           @AuthenticationPrincipal User user) {

        Job job = jobService.getJobById(id);

        boolean applied =
                jobApplicationService.apply(user, job);

        if (!applied) {
            return "redirect:/jobs?alreadyApplied";
        }

        return "redirect:/jobs?applied";
    }



}
