package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Job postJob(Job job);
    Optional<Job> findById(Long id);
    List<Job> findAllJobs();
    Page<Job> findAllJobsPaginated(Pageable pageable);
    List<Job> findJobsByEmployer(User employer);
    Page<Job> searchJobsByTitle(String title, Pageable pageable);
    Page<Job> searchJobsByLocation(String location, Pageable pageable);
    Page<Job> searchJobsByType(String jobType, Pageable pageable);
    void deleteJob(Long id);
}
