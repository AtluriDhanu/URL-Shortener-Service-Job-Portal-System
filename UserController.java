package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username already exists");
        }
        
        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email already exists");
        }
        
        if (result.hasErrors()) {
            return "register";
        }
        
        userService.registerUser(user);
        model.addAttribute("message", "Registration successful! Please login.");
        return "redirect:/login?success";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam("role") String role, Model model) {
        if ("EMPLOYER".equalsIgnoreCase(role)) {
            return "redirect:/employer/dashboard";
        } else {
            return "redirect:/applicant/dashboard";
        }
    }
}
