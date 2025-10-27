package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @GetMapping("/employer/dashboard")
    public String employerDashboard(Authentication authentication, Model model) {
        User employer = userService.findByUsername(authentication.getName()).orElse(null);
        List<Job> postedJobs = jobService.findJobsByEmployer(employer);

        model.addAttribute("employer", employer);
        model.addAttribute("jobs", postedJobs);
        return "employer-dashboard";
    }

    @GetMapping("/post")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "post-job";
    }

    @PostMapping("/post")
    public String postJob(@Valid @ModelAttribute("job") Job job,
                          BindingResult result,
                          Authentication authentication,
                          Model model) {
        if (result.hasErrors()) {
            return "post-job";
        }

        User employer = userService.findByUsername(authentication.getName()).orElse(null);
        job.setEmployer(employer);
        jobService.postJob(job);

        model.addAttribute("message", "Job posted successfully!");
        return "redirect:/job/employer/dashboard";
    }
}
