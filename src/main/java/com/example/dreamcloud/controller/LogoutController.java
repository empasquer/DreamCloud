package com.example.dreamcloud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public String logout(HttpServletRequest request, SessionStatus sessionStatus,
                         RedirectAttributes redirectAttributes) {
        request.getSession().invalidate();
        sessionStatus.setComplete();

        //I know why this isn't shown anywhere, I haven't called it in the HTML
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out.");
        return "redirect:/login";
    }
}
