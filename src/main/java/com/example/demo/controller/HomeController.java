package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.model.ProfileType;
import com.example.demo.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    private final ProfileService profileService;

    public HomeController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("profiles", profileService.getAllProfiles());
        model.addAttribute("profileTypes", ProfileType.values());
        return "dashboard";
    }

    @PostMapping("/create-profile")
    public String createProfile(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String department,
            @RequestParam ProfileType type,
            Model model) {

        Profile profile = Profile.builder()
                .fullName(fullName)
                .email(email)
                .phone(phone)
                .department(department)
                .type(type)
                .build();

        profileService.createProfile(profile);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
