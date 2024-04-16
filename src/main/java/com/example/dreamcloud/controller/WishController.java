package com.example.dreamcloud.controller;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.service.AuthenticationService;
import com.example.dreamcloud.service.ProfileService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class WishController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    WishService wishService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    ProfileService profileService;

    @GetMapping("/{profileUsername}/wishlist/{wishlistId}/wish/{wishId}")

    public String wish(@PathVariable int wishId, Model model, HttpSession session,
                       @PathVariable String profileUsername, HttpServletRequest request,
                       @PathVariable String wishlistId) {


        boolean isLoggedIn = authenticationService.isUserLoggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("loggedIn", isLoggedIn);


        // CHeck if user is authorized on this page
        boolean isAuthorized = authenticationService.checkIfAuthorized(request);
        model.addAttribute("isAuthorized", isAuthorized);


        // Retrieve profile information
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);

        // Retrieve wishlists form profile
        List<Wishlist> wishlists = wishlistService.getWishlistsFromProfileUsername(profile.getProfileUsername());
        profile.setWishlists((ArrayList<Wishlist>) wishlists);


        // Retrieve wish information
        Wish wish = wishService.getWishFromWishId(wishId);

        System.out.println("wish reserved:" + wish.isWishIsReserved());

        /*int wishlistId = wish.getWishlistId();*/

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
        } else {
            // if wish not found go back to wishlist
            return "redirect:/home/" + profileUsername + "/wishlist/" + wishlistId;
        }

    }

    @PostMapping("/delete-wish/{wishId}")
    public String deleteWish(@PathVariable int wishId, HttpSession session) {

        // Retrieve wish information
        Wish wish = wishService.getWishFromWishId(wishId);
        int wishlistId = wish.getWishlistId();

        wishService.deleteWishFromWishId(wishId);
        System.out.println("Wish is now deleted");

        String profileUsername = session.getAttribute("username").toString();

        return "redirect:/" + profileUsername + "/wishlist/" + wishlistId;
    }


    @GetMapping("/{profileUsername}/wishlist/{wishlistId}/create_wish")
    public String createWish(Model model, HttpSession session,
                             @PathVariable int wishlistId,
                             @PathVariable String profileUsername) {

        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);
        // Retrieve profile information
        Profile profile = authenticationService.getLoggedInUserProfile();
        model.addAttribute("profile", profile);

        //Retrieve wishlist information
        Wishlist wishlist = wishlistService.getWishlistFromWishlistId(wishlistId);
        model.addAttribute("wishlist", wishlist);

        return "home/create_wish";
    }


    @PostMapping("/{profileUsername}/wishlist/{wishlistId}/create_wish")
    public String createWish(@PathVariable int wishlistId,
                             @PathVariable String profileUsername,
                             @RequestParam String wishName,
                             @RequestParam String wishDescription,
                             @RequestParam double wishPrice,
                             @RequestParam("wishPicture") MultipartFile wishPicture,
                             HttpSession session) {
        Profile profile = profileService.getProfileFromUsername(profileUsername);

        byte[] pictureData = null;
        if (!wishPicture.isEmpty()) {
            try {
                pictureData = wishPicture.getBytes();
            } catch (IOException e) {
                // Handle IOException
                return "redirect:/error";
            }
        }

        // Call the createWish method from WishService to create the wish
        wishService.createWish(wishName, wishDescription, wishPrice, Optional.ofNullable(pictureData), wishlistId);

        // Redirect to the wishlist page after creating the wish
        return "redirect:/" + profile.getProfileUsername() + "/wishlist/" + wishlistId;
    }

    @PostMapping("/{profileUsername}/reserve-wish/wishlist/{wishlistId}/wish/{wishId}")
    public String reserveWish(@PathVariable String profileUsername, @PathVariable String wishlistId, @PathVariable String wishId, boolean reserve) {
        int wishIdInt = Integer.parseInt(wishId); // Convert wishId to integer
        wishService.reserveWish(wishIdInt, reserve);
        // Redirect to the specific wish page
        return "redirect:/" + profileUsername + "/wishlist/" + wishlistId + "/wish/" + wishId;
    }
}