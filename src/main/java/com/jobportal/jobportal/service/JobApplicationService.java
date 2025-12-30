package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.JobApplication;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public boolean apply(User user, Job job) {

        boolean alreadyApplied =
                repository.existsByUserIdAndJobId(user.getId(), job.getId());

        if (alreadyApplied) {
            return false;
        }

        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setJob(job);
        repository.save(application);

        return true;
    }

    public List<JobApplication> getApplicationsByJob(Long jobId) {
        return repository.findByJobId(jobId);
    }

    public List<JobApplication> getApplicationsForRecruiter(User recruiter) {
        return repository.findByJobRecruiter(recruiter);
    }

}
