package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application applyForJob(Application application);
    List<Application> findApplicationsByApplicant(User applicant);
    List<Application> findApplicationsByJob(Job job);
    Optional<Application> findById(Long id);
    boolean hasApplied(Job job, User applicant);
    void updateApplicationStatus(Long applicationId, String status);
}
