package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.service.ProfileService;
import com.example.dreamcloud.service.WishService;
import com.example.dreamcloud.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    WishService wishService;

    @GetMapping("/profile{profileUsername}")
    public String profile(@PathVariable String profileUsername, Model model) {
        Profile profile = profileService.getProfileFromUsername(profileUsername);
        if (profile != null) {
            model.addAttribute("profile", profile);
            return "home/profile";
        } else {
            //Profile not found
            return "home/index";
        }
    }

    @PostMapping("/create_profile")
    public String createProfile(@RequestParam String profileFirstname, @RequestParam String profileLastName, @RequestParam String profileUsername, @RequestParam String profilePassword, @RequestParam("profilePicture") Optional<MultipartFile> profilePicture) {
        //Returns null if picture isn't there
        byte[] pictureData = profilePicture.map(p -> {
            try {
                return p.getBytes();
            } catch (IOException e) {
                return null;
            }
        }).orElse(null);

        profileService.createProfile(profileFirstname, profileLastName, profileUsername, profilePassword, Optional.ofNullable(pictureData));
        return "redirect:/profile{profileUsername}";
    }


}
