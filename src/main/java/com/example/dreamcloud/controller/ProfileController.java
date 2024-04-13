package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
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
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.HTML;
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

        // Check if the user is authorized
        boolean isAuthorized = authenticationService.isAuthorized(request);
        model.addAttribute("loggedIn", loggedIn);
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


    @GetMapping("/create_profile")
    public String newProfile(Model model, HttpSession session) {
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);
        return "home/create_profile";
    }


    @PostMapping("/new_profile")
    public String createProfile(@RequestParam String profileUsername, @RequestParam String profileFirstname, @RequestParam String profileLastName, @RequestParam String profilePassword, @RequestParam("profilePicture") Optional<MultipartFile> profilePicture) {

        //Returns null if picture isn't there
        byte[] pictureData = profilePicture.map(p -> {
            try {
                return p.getBytes();
            } catch (IOException e) {
                return null;
            }
        }).orElse(null);

        profileService.createProfile(profileUsername, profileFirstname, profileLastName, profilePassword, Optional.ofNullable(pictureData));
        return "redirect:/profile/" + profileUsername;
    }


}
