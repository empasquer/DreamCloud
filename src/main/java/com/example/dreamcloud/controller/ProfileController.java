package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.service.ProfileService;
import com.example.dreamcloud.service.WishService;
import com.example.dreamcloud.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
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

    @GetMapping("/profile/{profileUsername}")
    public String profile(Model model, @PathVariable String profileUsername) {
        profileUsername = "john_doe";
        Profile profile = profileService.getProfileFromUsername(profileUsername);

        if (profile != null) {
            profile.setWishlists((ArrayList<Wishlist>)wishlistService.getWishlistsFromProfileUsername(profileUsername));
            ArrayList<Wishlist> wishlists = profile.getWishlists();

            model.addAttribute("profile", profile);
            model.addAttribute("wishlists", wishlists);
            return "home/profile";
        } else {
            //Profile not found... should maybe be error page?
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
