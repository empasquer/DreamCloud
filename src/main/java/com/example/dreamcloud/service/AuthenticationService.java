package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthenticationService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private HttpSession session;

    // basically just checks if the info matches the database
    public boolean authenticateUser(String username, String password) {
        Profile profile = profileRepository.getProfileFromUsername(username);
        if (profile != null) {
            if (password.equals(profile.getProfilePassword())) {
                session.setAttribute("loggedIn", true); // Set "loggedIn" attribute in session
                return true; // Authentication successful
            } else {
                return false; // Password mismatch
            }
        } else {
            return false; // User not found
        }
    }


    // returns true if session attribute loggedIn is not null and loggedIn == true.
    // Combinations: loggedIn is not null and true - returns true. User have logged in
    //               loggedIn is not null and false - returns false. User have logged out
    //               loggedIn is null - returns false. User have not logged in
    // We cast loggedIn as boolean because session can return attributes as Objects.
    public boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn");
    }

    //Gets profile from session. Username is set in login-controller
    public Profile getLoggedInUserProfile() {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return profileRepository.getProfileFromUsername(username);
        }
        return null;
    }

    //Basically checking if the session username matches the URL username
    public boolean isAuthorized(HttpServletRequest request) {

            //get username from URL
            String requestedUsername = getUsernameFromProfileRequest(request);
        System.out.println(requestedUsername);
            //get username from session
            String loggedInUsername = (String) session.getAttribute("username");
        System.out.println(loggedInUsername);
            if (requestedUsername != null && requestedUsername.equals(loggedInUsername)){
               return true;
            } else {
                //if URL username doesn't match session username then return false
                return false;
            }
    }

    public String getUsernameFromProfileRequest(HttpServletRequest request) {
        // Get the request URL
        String url = request.getRequestURL().toString();

        // Get an array of all the individually segments of the path:
        String[] segments = url.split("/");

        System.out.println(segments[3]);

        // The username will always be the third segment
            return segments[3];

    }

}