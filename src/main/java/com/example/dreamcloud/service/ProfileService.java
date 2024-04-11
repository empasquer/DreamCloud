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
    ProfileRepository profileRespository;

    public List<Profile> getProfiles() {
        return profileRespository.getProfiles();
    }

    public Profile getProfileFromUsername(String profileUsername) {
        return profileRespository.getProfileFromUsername(profileUsername);
    }

    public void createProfile(String profileFirstname, String profileLastName, String profileUsername, String profilePassword, Optional<byte[]> profilePicture) {
        profileRespository.createProfile(profileFirstname, profileLastName, profileUsername, profilePassword, profilePicture);
    }
}
