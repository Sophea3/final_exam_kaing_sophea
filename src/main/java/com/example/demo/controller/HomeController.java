package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.model.ProfileType;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.ProfileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class HomeController {

    private final ProfileService profileService;
    private final FileStorageService fileStorageService;

    public HomeController(ProfileService profileService, FileStorageService fileStorageService) {
        this.profileService = profileService;
        this.fileStorageService = fileStorageService;
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
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String department,
            @RequestParam ProfileType type,
            @RequestParam(value = "photoFile", required = false) MultipartFile photoFile) {

        Profile profile = Profile.builder()
                .fullName(fullName)
                .email(email)
                .phone(phone)
                .department(department)
                .type(type)
                .build();

        if (photoFile != null && !photoFile.isEmpty()) {
            String fileName = fileStorageService.saveFile(photoFile);
            profile.setPhotoFileName(fileName);
        }

        profileService.createProfile(profile);

        return "redirect:/";
    }

    @PostMapping("/update-profile/{id}")
    public String updateProfile(
            @PathVariable Long id,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String department,
            @RequestParam ProfileType type) {

        Profile profile = Profile.builder()
                .fullName(fullName)
                .email(email)
                .phone(phone)
                .department(department)
                .type(type)
                .build();

        profileService.updateProfile(id, profile);
        return "redirect:/";
    }

    @PostMapping("/delete-profile/{id}")
    public String deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return "redirect:/";
    }

    @GetMapping("/api/photos/{fileName}")
    public ResponseEntity<Resource> servePhoto(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("uploads").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            // fall through
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
