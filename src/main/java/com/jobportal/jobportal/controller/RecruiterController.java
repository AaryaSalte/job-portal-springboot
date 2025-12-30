package com.jobportal.jobportal.controller;
import java.util.List;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.JobApplication;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.service.JobApplicationService;
import com.jobportal.jobportal.service.JobService;
import com.jobportal.jobportal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RecruiterController {

    private final JobApplicationService jobApplicationService;
    private final UserService userService;
    private final JobService jobService;

    public RecruiterController(JobApplicationService jobApplicationService,
                               UserService userService,
                               JobService jobService) {
        this.jobApplicationService = jobApplicationService;
        this.userService = userService;
        this.jobService = jobService;
    }

    @GetMapping("/recruiter/applications")
    public String viewApplications(Model model, Authentication authentication) {

        User recruiter = userService.findByEmail(authentication.getName());
        List<JobApplication> applications =
                jobApplicationService.getApplicationsForRecruiter(recruiter);

        model.addAttribute("applications", applications);
        return "recruiter-applications";
    }

    @GetMapping("/recruiter/jobs/new")
    public String showJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-form";
    }

    @PostMapping("/recruiter/jobs/add")
    public String saveJob(Job job, Authentication authentication) {

        User recruiter = userService.findByEmail(authentication.getName());
        job.setRecruiter(recruiter);

        jobService.saveJob(job);
        return "redirect:/recruiter/jobs?created";
    }

    @GetMapping("/recruiter/jobs")
    public String recruiterJobs(Model model, Authentication authentication) {

        User recruiter = userService.findByEmail(authentication.getName());
        model.addAttribute("jobs", jobService.getJobsByRecruiter(recruiter));

        return "recruiter-jobs";
    }

    @GetMapping("/recruiter/dashboard")
    public String recruiterDashboard() {
        return "recruiter-dashboard";
    }


}

