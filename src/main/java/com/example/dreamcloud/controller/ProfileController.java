package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.service.ProfileService;
import com.example.dreamcloud.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

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
        model.addAttribute("profile", profile);

        profile.setWishlists((ArrayList<Wishlist>)wishlistService.getWishlistsFromProfileUsername(profileUsername));

        ArrayList<Wishlist> wishlists = profile.getWishlists();


        model.addAttribute("wishlists", wishlists);
        return ("home/profile");
    }
}
