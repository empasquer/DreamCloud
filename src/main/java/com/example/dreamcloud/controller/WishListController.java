package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.service.ProfileService;
import com.example.dreamcloud.service.WishService;
import com.example.dreamcloud.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WishListController {
    @Autowired WishlistService wishlistService;
    @Autowired
    WishService wishService;
    @Autowired
    ProfileService profileService;

/*    @GetMapping("/wishlist{wishlistId}")
    public String wishlist(@PathVariable int wishlistId, Model model) {


        *//* Confusing shit *//*


*//*        String profileUsername = "john_doe";
        Profile profile = profileService.getProfileFromUsername(profileUsername);

        ArrayList<Wishlist> wishlists = profile.getWishlists();

        model.addAttribute("profile", profile);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("wishlist", wishlistService.getWishlistFromWishlistId(wishlistId));

        model.addAttribute("wishes", wishService.getWishesFromWishlistId()); *//*

        return ("/home/wishlist");
    }*/


    @GetMapping("/wishlist/{wishlistId}")
    public String wishlist(@PathVariable int wishlistId, Model model) {
        Wishlist wishlist = wishlistService.getWishlistFromWishlistId(wishlistId);

        model.addAttribute("wishlist", wishlist);


        List<Wish> wishes = wishService.getWishesFromWishListId(wishlistId);
        wishlist.setWishes((ArrayList<Wish>) wishes);

        model.addAttribute("wishes", wishlist.getWishes());

            return "home/wishlist";

    }
}
