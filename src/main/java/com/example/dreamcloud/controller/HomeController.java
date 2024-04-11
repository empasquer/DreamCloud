package com.example.dreamcloud.controller;

import com.example.dreamcloud.service.AuthenticationService;
import com.example.dreamcloud.service.WishService;
import com.example.dreamcloud.service.WishlistService;
import com.example.dreamcloud.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ProfileService profileService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    WishService wishService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/")
    public String index(Model model, HttpSession session ){
        boolean loggedIn = authenticationService.isUserLoggedIn(session);
        model.addAttribute("loggedIn", loggedIn);
        return "home/index";
    }


    /* Testing page */
    @GetMapping("/tests")
    public String tests(Model model) {
        model.addAttribute("profiles", profileService.getProfiles());
        model.addAttribute("wishlists", wishlistService.getWishlists());
        model.addAttribute("wishes", wishService.getWishes());


        return "home/tests";
    }



}
