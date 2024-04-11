package com.example.dreamcloud.controller;

import com.example.dreamcloud.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login(){
        return "home/login";
    }
    // HttpServletRequest and HttpServletResponse: Takes the information from the internet and brings it back again.
    // @RequestParam String username, @RequestParam String password:  Containers that hold the username and password that someone types into the login form.
    @PostMapping("/login")
    public String login(HttpServletRequest request,  @RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes){
        if (authenticationService.authenticateUser(username, password)){
            request.getSession().setAttribute("username", username);
            return "redirect:/profile/" + username;
        } else
            // If authentication fails, redirect to login page with error message
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
        System.out.println("invalid password");
            return "redirect:/login";

        }
    }

