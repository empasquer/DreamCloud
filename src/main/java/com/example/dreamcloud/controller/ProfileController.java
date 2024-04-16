package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.service.AuthenticationService;
import com.example.dreamcloud.service.ProfileService;
import com.example.dreamcloud.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{profileUsername}/profile")
    public String profile(Model model, @PathVariable String profileUsername, HttpSession session, HttpServletRequest request) {
        // Check if user is logged in
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        if (!loggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("loggedIn", loggedIn);

        // Check if the user is authorized
        boolean isAuthorized = authenticationService.checkIfAuthorized(request);
        model.addAttribute("isAuthorized", isAuthorized);

        // Get the profile
        Profile profile = profileService.getProfileFromUsername(profileUsername);
        if (profile == null) {
            // Profile not found... should maybe be error page or something else? More for searching
            return "home/index";
        }

        // Set wishlists for the profile
        profile.setWishlists((ArrayList<Wishlist>) wishlistService.getWishlistsFromProfileUsername(profileUsername));
        ArrayList<Wishlist> wishlists = profile.getWishlists();

        // Add profile and wishlists to the model
        model.addAttribute("profile", profile);
        model.addAttribute("wishlists", wishlists);

        return "home/profile";
    }

    @PostMapping("/{profileUsername}/delete_profile")
    public String deleteProfile(@PathVariable String profileUsername, HttpSession session) {
        profileService.deleteProfile(profileUsername);
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/create_profile")
    public String newProfile(Model model, HttpSession session) {
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);
        return "home/create_profile";
    }


    @PostMapping("/new_profile")

    public String createProfile(Model model, @RequestParam String profileUsername, @RequestParam String profileFirstname, @RequestParam String profileLastName, @RequestParam String profilePassword, @RequestParam("profilePicture") MultipartFile profilePicture) {
        // Check if the username exists in the database
        Profile existingProfile = profileService.getProfileFromUsername(profileUsername);
        if (existingProfile != null) {
            // Username taken
            model.addAttribute("message", "This username is taken");
            return "home/create_profile"; // Returns same page with the message
        } else {
            byte[] pictureData = null;
            if (!profilePicture.isEmpty()) {
                try {
                    pictureData = profilePicture.getBytes();
                } catch (IOException e) {
                    return "redirect:/error";
                }
            }
            profileService.createProfile(profileUsername, profileFirstname, profileLastName, profilePassword, Optional.ofNullable(pictureData));
            return "redirect:/login" ;
        }
    }

    @GetMapping("/{profileUsername}/edit_profile")
    public String showExistingProfile(@PathVariable String profileUsername, Model model, HttpSession session) {
        boolean isLoggedIn = authenticationService.isUserLoggedIn(session);
        if (!isLoggedIn) {
            return "redirect:/login";
        }

        Profile loggedInProfile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", loggedInProfile);

        return "home/edit_profile";
    }

    @PostMapping("/{profileUsername}/edit_profile")
    public String editProfile(@PathVariable String profileUsername, @RequestParam String profileFirstname, @RequestParam String profileLastName, @RequestParam String profilePassword, @RequestParam("profilePicture") MultipartFile profilePicture, HttpSession session) {
        // Check if user is logged in
        boolean isLoggedIn = authenticationService.isUserLoggedIn(session);
        if (!isLoggedIn) {
            return "redirect:/login";
        }

        //Basically checks if there is a picture uploaded or not, if not it is just null
        Profile existingProfile = profileService.getProfileFromUsername(profileUsername);
        byte[] pictureData = existingProfile.getProfilePicture();
        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                pictureData = profilePicture.getBytes();
            } catch (IOException e) {
                return "redirect:/error";
            }
        }

        Optional<byte[]> pictureOptional;
        if (pictureData != null) {
            pictureOptional = Optional.of(pictureData);
        } else {
            pictureOptional = Optional.empty();
        }

        profileService.editProfile(profileUsername, profileFirstname, profileLastName, profilePassword, pictureOptional);
        return "redirect:/" + profileUsername + "/profile";
    }


}
