package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticateUser(String username, String password) {
        Profile profile = profileRepository.getProfileFromUsername(username);
        System.out.println("profile: " + profile);
        if (profile != null) {
            System.out.println(profile.getProfilePassword());
            System.out.println(password);
            if (passwordEncoder.matches(password, profile.getProfilePassword())) {
                System.out.println("password macthes");
                return true; // Authentication successful
            } else {
                System.out.println("no match");
                return false; // Password mismatch
            }
        } else {
            return false; // User not found
        }
    }
}