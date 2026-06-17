package com.example.demo.service;



import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile createProfile(Profile profile) {
        if (profile.getUuid() == null) {
            profile.setUuid(UUID.randomUUID().toString());
        }
        if (profile.getRegistrationNumber() == null) {
            long count = profileRepository.count() + 1;
            String year = String.valueOf(Year.now().getValue());
            String typePrefix = profile.getType().name().substring(0, 3).toUpperCase();
            profile.setRegistrationNumber(year + "-" + typePrefix + "-" + String.format("%03d", count));
        }
        return profileRepository.save(profile);
    }

    public Profile getProfile(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public Profile updateProfile(Long id, Profile updatedProfile) {
        Profile profile = getProfile(id);

        profile.setFullName(updatedProfile.getFullName());
        profile.setEmail(updatedProfile.getEmail());
        profile.setPhone(updatedProfile.getPhone());
        profile.setDepartment(updatedProfile.getDepartment());
        profile.setType(updatedProfile.getType());

        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
