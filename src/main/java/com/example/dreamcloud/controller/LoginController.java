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

    @PostMapping("/login")
    public String login(HttpServletRequest request,  @RequestParam String username,
                        @RequestParam String password, RedirectAttributes redirectAttributes) {

        //AuthenticateUser --> service --> repository, checks for matching info in database
        if (authenticationService.authenticateUser(username, password)){
            request.getSession().setAttribute("username", username); //sets username to session - used in header
            return "redirect:/"+ username + "/profile";
        } else {
            //If authentication fails, redirect to login page with error message that we can call in the HTML
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }

    }
}

