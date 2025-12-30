package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.repository.JobApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jobportal.jobportal.entity.User;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;


    public JobService(JobRepository jobRepository,
                      JobApplicationRepository jobApplicationRepository) {
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }


    public void saveJob(Job job) {
        jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteJob(Long jobId) {

        // 1. Delete job applications first
        jobApplicationRepository.deleteByJobId(jobId);

        // 2. Delete the job
        jobRepository.deleteById(jobId);
    }

    public List<Job> getJobsByRecruiter(User recruiter) {
        return jobRepository.findByRecruiter(recruiter);
    }

    public List<Job> searchJobs(String keyword) {
        return jobRepository
                .findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
                        keyword, keyword, keyword
                );
    }


}
