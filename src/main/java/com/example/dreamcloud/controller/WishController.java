package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.service.AuthenticationService;
import com.example.dreamcloud.service.WishService;
import com.example.dreamcloud.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WishController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    WishService wishService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/wish/{wishId}")
    public String wish(@PathVariable int wishId, Model model, HttpSession session){
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);

        // Retrieve profile information
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);

        // Retrieve wishlists form profile
        List<Wishlist> wishlists = wishlistService.getWishlistsFromProfileUsername(profile.getProfileUsername());
        profile.setWishlists((ArrayList<Wishlist>) wishlists);

        // Retrieve wish information
        Wish wish = wishService.getWishFromWishId(wishId);



        int wishlistId = 0;

        if (wish != null) {
            // Check if the wish belongs to any of the user's wishlists
          /*  boolean wishBelongsToUser = false;
            for (Wishlist wishlist : profile.getWishlists()) {
                for (Wish userWish : wishlist.getWishes()) {
                    if (userWish.getWishId() == wishId) {
                        System.out.println("Wish belongs to user in one of the wishlists");
                        wishlistId = wishlist.getWishlistId();
                        wishBelongsToUser = true;
                        break;
                    }
                }
            }
            if (wishBelongsToUser) {
                model.addAttribute("wish", wish);
                return "/home/wish"; // Return the name of your wish details template
            } else {
                return "redirect:/home/wishlist/" + wishlistId;
            }*/

            model.addAttribute("wish", wish);

             return "/home/wish";
        }


        else {
            // if wish not found go back to wishlist
            return "redirect:/home/wishlist/" + wishlistId;
        }

    }
}

