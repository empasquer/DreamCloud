package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.service.AuthenticationService;
import com.example.dreamcloud.service.ProfileService;
import com.example.dreamcloud.service.WishService;
import com.example.dreamcloud.service.WishlistService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
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
public class WishListController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    WishService wishService;
    @Autowired
    ProfileService profileService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{profileUsername}/wishlist/{wishlistId}")
    public String wishlist(@PathVariable String profileUsername, @PathVariable int wishlistId, Model model, HttpSession session, HttpServletRequest request) {
        // Check if user is logged in
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        if (!loggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("profileUsername", profileUsername);

        model.addAttribute("profileUsername", profileUsername);
        // Check if the user is authorized
        boolean isAuthorized = authenticationService.checkIfAuthorized(request);
        model.addAttribute("isAuthorized", isAuthorized);

        // Retrieve profile information
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);

        // Retrieve wishlist information
        Wishlist wishlist = wishlistService.getWishlistFromWishlistId(wishlistId);
        if (wishlist == null) {
            return "redirect:/login";
        }

        // Retrieve wishes for the wishlist
        List<Wish> wishes = wishService.getWishesFromWishListId(wishlistId);
        wishlist.setWishes((ArrayList<Wish>) wishes);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wishes", wishlist.getWishes());

        return "home/wishlist";
    }


    @PostMapping("/{profileUsername}/wishlist/{wishlistId}/share")
    public String openPopup(HttpSession session, HttpServletRequest request, Model model, @PathVariable String profileUsername, @PathVariable String wishlistId) {
        // Check if user is logged in
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        if (!loggedIn) {
            return "redirect:/login";
        }


        model.addAttribute(profileUsername);
        model.addAttribute(wishlistId);
        Wishlist wishlist = wishlistService.getWishlistFromWishlistId(Integer.parseInt(wishlistId));
        model.addAttribute(wishlist);


        // Find link to share
        String domain = request.getServerName();
        System.out.println(domain);
        String shareLink = "http://" + domain + ":8080/" + profileUsername + "/wishlist/" + wishlistId;
        System.out.println(shareLink);


        model.addAttribute("shareLink", shareLink);


        return "home/share-popup";
    }

    @GetMapping("/create_wishlist")
    public String createWishlist(Model model, HttpSession session) {
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);
        // Retrieve profile information
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);
        return "home/create_wishlist";
    }

    @PostMapping("/create_wishlist")
    public String createWishlist(Model model, @RequestParam String title, @RequestParam String description, HttpSession session){
        String profileUsername = String.valueOf(session.getAttribute("username"));
        /*model.addAttribute(profileUsername);*/
        wishlistService.createWishlist(title,description, profileUsername);
        return "redirect:/"+ profileUsername + "/profile";
}


    @PostMapping("/{profileUsername}/delete-wishlist/{wishlistId}")
    public String deleteWishlist(@PathVariable String profileUsername, @PathVariable int wishlistId, HttpSession session) {
        wishlistService.deleteWishlist(wishlistId);
        return "redirect:/"+ profileUsername + "/profile";
    }

    @GetMapping("/{profileUsername}/edit-wishlist/{wishlistId}")
    public String showExistingWishlist(@PathVariable int wishlistId, Model model, HttpSession session) {
        boolean isLoggedIn = authenticationService.isUserLoggedIn(session);
        if (!isLoggedIn) {
            return "redirect:/login";
        }

        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);

        Wishlist wishlist = wishlistService.getWishlistFromWishlistId(wishlistId);
        model.addAttribute("wishlist", wishlist);

        return "home/edit_wishlist";
    }


    @PostMapping("/{profileUsername}/edit-wishlist/{wishlistId}")
    public String editWishlist(@PathVariable int wishlistId,
                               @RequestParam String title, @RequestParam String description,
                               HttpSession session) {
        boolean isLoggedIn = authenticationService.isUserLoggedIn(session);
        if (!isLoggedIn) {
            return "redirect:/login";
        }

        //Bliver nødt til at gøre det sådan da den eller sætter username til null
        String profileUsername = (String) session.getAttribute("username");
        if (profileUsername == null) {
            return "redirect:/login";
        }

        wishlistService.editWishlist(profileUsername, wishlistId, title, description);
        return "redirect:/" + profileUsername + "/wishlist/" + wishlistId;
    }







}