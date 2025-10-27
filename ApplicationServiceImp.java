package com.jobportal.serviceImp;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImp implements ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Override
    public Application applyForJob(Application application) {
        return applicationRepository.save(application);
    }
    
    @Override
    public List<Application> findApplicationsByApplicant(User applicant) {
        return applicationRepository.findByApplicant(applicant);
    }
    
    @Override
    public List<Application> findApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }
    
    @Override
    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }
    
    @Override
    public boolean hasApplied(Job job, User applicant) {
        return applicationRepository.existsByJobAndApplicant(job, applicant);
    }
    
    @Override
    public void updateApplicationStatus(Long applicationId, String status) {
        Optional<Application> app = applicationRepository.findById(applicationId);
        if (app.isPresent()) {
            Application application = app.get();
            application.setStatus(status);
            applicationRepository.save(application);
        }
    }
}
