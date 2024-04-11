package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.repository.ProfileRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session; // Inject HttpSession

    public boolean authenticateUser(String username, String password) {
        Profile profile = profileRepository.getProfileFromUsername(username);
        System.out.println("profile: " + profile);
        if (profile != null) {
            if (passwordEncoder.matches(password, profile.getProfilePassword())) {
                session.setAttribute("loggedIn", true); // Set "loggedIn" attribute in session
                return true; // Authentication successful
            } else {
                return false; // Password mismatch
            }
        } else {
            return false; // User not found
        }
    }


    public boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn");
    }
    public Profile getLoggedInUserProfile() {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return profileRepository.getProfileFromUsername(username);
        }
        return null;
    }
}