package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getProfiles() {
        return profileRepository.getProfiles();
    }

    public Profile getProfileFromUsername(String profileUsername) {
        return profileRepository.getProfileFromUsername(profileUsername);
    }

    public String createProfile(String profileUsername, String profileFirstname, String profileLastName, String profilePassword, Optional<byte[]> profilePicture) {
        // Check if a profile with the given username already exists
        Profile existingProfile = profileRepository.getProfileFromUsername(profileUsername);

        if (existingProfile != null) {
            // Username is taken, return a message indicating so
            return "Username taken";
        } else {
            // Proceed with creating the new profile
            profileRepository.createProfile(profileUsername, profileFirstname, profileLastName, profilePassword, profilePicture);
            return "Profile created successfully";
        }
    }
}
