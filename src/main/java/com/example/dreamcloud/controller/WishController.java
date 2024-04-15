package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.service.AuthenticationService;
import com.example.dreamcloud.service.WishService;
import com.example.dreamcloud.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WishController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    WishService wishService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/delete-wish/{wishId}")
    public String deleteWish(@PathVariable int wishId) {

        // Retrieve wish information
        Wish wish = wishService.getWishFromWishId(wishId);
        int wishlistId = wish.getWishlistId();

        wishService.deleteWishFromWishId(wishId);
        System.out.println("Wish is now deleted");

        return "redirect:/wishlist/" + wishlistId;
    }

  @GetMapping("/{profileUsername}/wish/{wishId}")
        public String wish (@PathVariable String profileUsername,@PathVariable int wishId, Model model, HttpSession
        session, HttpServletRequest request){
            // Check if user is logged in
            boolean loggedIn = authenticationService.isUserLoggedIn(session);
            if (!loggedIn) {
                return "redirect:/login";
            }

            // Check if the user is authorized
            boolean isAuthorized = authenticationService.checkIfAuthorized(request);
            model.addAttribute("loggedIn", loggedIn);
            model.addAttribute("isAuthorized", isAuthorized);

            // Retrieve profile information
            Profile profile = authenticationService.getLoggedInUserProfile();
            model.addAttribute("profile", profile);

            // Retrieve wish information
            Wish wish = wishService.getWishFromWishId(wishId);
            if (wish == null) {
                // If wish not found, redirect to wishlist
                return "redirect:/home/wishlist/" + wishId;
            }

            model.addAttribute("wish", wish);
            return "/home/wish";
        }

    }

    /* copy/paste from create-wishlist, just to check that the button works
    @GetMapping("/create_wish")
    public String createWishlist(Model model, HttpSession session) {
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);
        // Retrieve profile information
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);
        return "home/create_wish";
    }

}*/


