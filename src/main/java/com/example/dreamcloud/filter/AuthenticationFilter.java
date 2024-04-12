package com.example.dreamcloud.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // retrieves the session. (false) means that if there is no session then it shouldn't create one
        boolean sessionExists = session != null; // true if the session is not null
        boolean usernameExists = false;
        if (sessionExists) {
            Object usernameAttribute = session.getAttribute("username");
            usernameExists = usernameAttribute != null; // looks if there is a username attached to the session and returns true/false
        }
        boolean loggedIn = sessionExists && usernameExists; // only logged in if a session exists and there is a username attached.
        // we add the username in the login-controller.
        if (loggedIn) {
            //get username from URL
            String requestedUsername = getUsernameFromProfileRequest(request);
            //get username from session
            String loggedInUsername = (String) session.getAttribute("username");

            if (requestedUsername != null && requestedUsername.equals(loggedInUsername)){

                  filterChain.doFilter(request, response);
            } else {
                //if URL username doesn't match session username then redirect to log in
                response.sendRedirect("/login");
            }
        }
        else {
            // if logged in is false then redirect to log in
            response.sendRedirect("/login");
        }
    }

    // Basically gets the username from the URL
    private String getUsernameFromProfileRequest(HttpServletRequest request) {
        // retrieve the URL
        String requestURL = request.getRequestURL().toString();
        // finds the last occurrence of / in the path and takes whatever comes after that (the username)
        int usernameIndex = requestURL.lastIndexOf('/') + 1;
        // checks if the usernameIndex is valid: it cant be negative, and it can't be more than the length of the URI.
        // we do this to avoid IndexOutOfBoundsException from substring in case something goes wrong.
        if (usernameIndex >= 0 && usernameIndex < requestURL.length()) {
            return requestURL.substring(usernameIndex);
        }
        return null;
    }
}
