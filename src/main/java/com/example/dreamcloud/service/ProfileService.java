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

    public Profile getProfileFromUsername(String profileUsername) {
        return profileRepository.getProfileFromUsername(profileUsername);
    }

    public String createProfile(String profileUsername, String profileFirstname, String profileLastName, String profilePassword, Optional<byte[]> profilePicture) {
        // Check if a profile with the given username already exists
        Profile existingProfile = profileRepository.getProfileFromUsername(profileUsername);

        if (existingProfile != null) {
            return "Username taken";
        } else {
            profileRepository.createProfile(profileUsername, profileFirstname, profileLastName, profilePassword, profilePicture);
            return "Profile created successfully";
        }
    }

    public void deleteProfile(String profileUsername) {
        profileRepository.deleteProfile(profileUsername);
    }

    public void editProfile(String profileUsername, String profileFirstName, String profileLastName, String profilePassword, Optional<byte[]> profilePicture) {
        profileRepository.editProfile(profileUsername, profileFirstName, profileLastName, profilePassword, profilePicture);
    }

}
