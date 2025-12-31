package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.JobApplication;
import com.jobportal.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobId(Long jobId);
    boolean existsByUserIdAndJobId(Long userId, Long jobId);
    void deleteByJobId(Long jobId);
    List<JobApplication> findByJobRecruiter(User recruiter);
    List<JobApplication> findByUser(User user);



}
