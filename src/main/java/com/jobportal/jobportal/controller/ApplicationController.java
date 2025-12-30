package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.service.JobApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApplicationController {

    private final JobApplicationService service;

    public ApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @GetMapping("/applications/job/{jobId}")
    public String viewApplications(@PathVariable Long jobId, Model model) {
        model.addAttribute("applications",
                service.getApplicationsByJob(jobId));
        return "job-applications";
    }
}
