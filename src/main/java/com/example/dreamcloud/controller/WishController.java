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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/delete-wish/{wishId}")
    public String deleteWish(@PathVariable int wishId, HttpSession session) {

        // Retrieve wish information
        Wish wish = wishService.getWishFromWishId(wishId);
        int wishlistId = wish.getWishlistId();

        wishService.deleteWishFromWishId(wishId);
        System.out.println("Wish is now deleted");

        String profileUsername = String.valueOf(session.getAttribute("username"));
        return "redirect:/profile/" + profileUsername + "/wishlist/" + wishlistId;
    }


    @GetMapping("/profile/{profileUsername}/wishlist/{wishlistId}/wish/{wishId}")
    public String wish(@PathVariable String profileUsername, @PathVariable int wishlistId, @PathVariable int wishId, Model model, HttpSession session){
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



        //int wishlistId = wish.getWishlistId();

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

    /* copy/paste from create-wishlist, just to check that the button works */
    @GetMapping("/create_wish")
    public String createWishlist(Model model, HttpSession session) {
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);
        // Retrieve profile information
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);
        return "home/create_wish";
    }

}

