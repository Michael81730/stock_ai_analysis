package com.mc.stock_ai_analysis.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.stock_ai_analysis.entity.AuthRequest;
import com.mc.stock_ai_analysis.entity.UserInfo;
import com.mc.stock_ai_analysis.service.JwtService;
import com.mc.stock_ai_analysis.service.UserInfoService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserInfoService userInfoService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userInfoService = userInfoService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        System.out.println("Login Request: " + authRequest.getUsername() + ", " + authRequest.getPassword());
        Authentication authentication;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        System.out.println("Authenticated User: " + authentication.getPrincipal());
        System.out.println("Credentials: " + authentication.getCredentials());
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String refreshToken) {
        System.out.println("Logout Request: " + refreshToken);
        return "Logged out successfully";
    }
}