package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.FileStorageService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    // Applicant Dashboard
    @GetMapping("/applicant/dashboard")
    public String applicantDashboard(Authentication authentication, Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size) {
        User applicant = userService.findByUsername(authentication.getName()).orElse(null);
        List<Application> myApplications = applicationService.findApplicationsByApplicant(applicant);
        
        model.addAttribute("applicant", applicant);
        model.addAttribute("applications", myApplications);
        return "applicant-dashboard";
    }

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId,
                             @RequestParam("coverLetter") String coverLetter,
                             @RequestParam("resume") MultipartFile resume,
                             Authentication authentication,
                             Model model) {
        User applicant = userService.findByUsername(authentication.getName()).orElse(null);
        Job job = jobService.findById(jobId).orElse(null);
        
        if (applicationService.hasApplied(job, applicant)) {
            model.addAttribute("error", "You have already applied for this job");
            return "redirect:/job/" + jobId + "?error";
        }
        
        String resumePath = null;
        if (!resume.isEmpty()) {
            resumePath = fileStorageService.storeFile(resume);
        }
        
        Application application = new Application();
        application.setJob(job);
        application.setApplicant(applicant);
        application.setCoverLetter(coverLetter);
        application.setResumePath(resumePath);
        
        applicationService.applyForJob(application);
        
        return "redirect:/application/applicant/dashboard?success";
    }
    
    @GetMapping("/job/{jobId}/applicants")
    public String viewApplicants(@PathVariable Long jobId, Model model) {
        Job job = jobService.findById(jobId).orElse(null);
        List<Application> applications = applicationService.findApplicationsByJob(job);
        
        model.addAttribute("job", job);
        model.addAttribute("applications", applications);
        return "applicants-list";
    }
    
    @PostMapping("/status/{applicationId}")
    public String updateStatus(@PathVariable Long applicationId,
                              @RequestParam("status") String status,
                              @RequestParam("jobId") Long jobId) {
        applicationService.updateApplicationStatus(applicationId, status);
        return "redirect:/application/job/" + jobId + "/applicants";
    }
}
