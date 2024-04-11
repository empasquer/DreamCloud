package com.example.dreamcloud.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    // HttpServletRequest and HttpServletResponse: Takes the information from the internet and brings it back again.
    // @RequestParam String username, @RequestParam String password:  Containers that hold the username and password that someone types into the login form.
    @PostMapping("/login")
    public String login(HttpServletRequest request,  @RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes){
        if (authenticationService.authenticateUser(username, password)){
            request.getSession().setAttribute("username", username);
            return "redirect:/index";
        }
        catch (AuthenticationException e) {
            // If authentication fails, redirect to login page with error message
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:";

        }
    }
}
