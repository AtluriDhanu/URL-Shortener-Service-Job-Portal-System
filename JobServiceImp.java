package com.jobportal.serviceImp;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImp implements JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    @Override
    public Job postJob(Job job) {
        return jobRepository.save(job);
    }
    
    @Override
    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }
    
    @Override
    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }
    
    @Override
    public Page<Job> findAllJobsPaginated(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }
    
    @Override
    public List<Job> findJobsByEmployer(User employer) {
        return jobRepository.findByEmployer(employer);
    }
    
    @Override
    public Page<Job> searchJobsByTitle(String title, Pageable pageable) {
        return jobRepository.findByTitleContainingIgnoreCase(title, pageable);
    }
    
    @Override
    public Page<Job> searchJobsByLocation(String location, Pageable pageable) {
        return jobRepository.findByLocationContainingIgnoreCase(location, pageable);
    }
    
    @Override
    public Page<Job> searchJobsByType(String jobType, Pageable pageable) {
        return jobRepository.findByJobTypeContainingIgnoreCase(jobType, pageable);
    }
    
    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
