package com.springboot.login.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.springboot.login.jwt.JwtUtils;
import com.springboot.login.model.Users;
import com.springboot.login.services.UsersServices;

@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UsersServices userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/getusers")
    public ResponseEntity<List<Users>> getUsers() {

        return new ResponseEntity<List<Users>>(userService.getAllUser(), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public String addUser(@RequestBody Users usr) {
        usr.setRole("user");
        return userService.insertUser(usr);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> login(@RequestBody Users usr, HttpServletResponse response) {

        log.info("login credential {}",usr);

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usr.getEmail(), usr.getPassword()));

        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "bad request");
            map.put("status", false);

            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setHttpOnly(true);  // Secure, prevents JavaScript access
        jwtCookie.setSecure(true);   // Set to `true` if using HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setAttribute("SameSite", "None"); // Allow cross-origin requests// Available for all endpoints
        jwtCookie.setMaxAge(60 * 60); // Expires in 1 hour
        // Add cookie to response
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Logged in successfully");
    }

    @GetMapping("/validateJwt")
    public ResponseEntity<String> validateJwtToken(){
        return ResponseEntity.ok("jwt validated");
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        // Create an empty cookie with the same name and set it to expire immediately
        log.debug("Entered inside logout api");
        ResponseCookie cookie = ResponseCookie.from("jwt", "") // Cookie name
                .httpOnly(true)
                .secure(true) // Set to true if using HTTPS
                .path("/")
                .maxAge(0) // Expire the cookie immediately
                .sameSite("None")
                .build();

        // Add the cookie to the response
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().body("Logged out successfully");
    }
}
