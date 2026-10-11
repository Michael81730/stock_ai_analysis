package com.mc.stock_ai_analysis.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.stock_ai_analysis.entity.LoginRequestBody;
import com.mc.stock_ai_analysis.entity.SignupRequestBody;
import com.mc.stock_ai_analysis.service.JwtService;
import com.mc.stock_ai_analysis.service.UserInfoService;
import com.mc.stock_ai_analysis.utility.UserFactory;

// POST http://127.0.0.1:8080/auth/signup
// POST http://127.0.0.1:8080/auth/login
// POSThttp://127.0.0.1:8080/auth/logout

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
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequestBody signupRequestBody) {
        System.out.println("Signup Request: " + signupRequestBody.getName() + ", " + signupRequestBody.getEmail() + ", " + signupRequestBody.getPassword());
        
        Optional<String> userIdOptional = userInfoService.loadUserIdByEmail(signupRequestBody.getEmail());

        if (userIdOptional.isPresent()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("message", "User already exists with email: " + signupRequestBody.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }

        // UserInfo newUser = new UserInfo();
        // newUser.setName(signupRequestBody.getName());
        // newUser.setEmail(signupRequestBody.getEmail());
        // newUser.setPassword(signupRequestBody.getPassword());
        // newUser.setRoles("USER");
        
        String result = userInfoService.addUser(UserFactory.createUserInfo(signupRequestBody.getName(), signupRequestBody.getEmail(), signupRequestBody.getPassword()));

        Map<String, Object> responseBody = new HashMap<>();

        responseBody.put("success", true);
        responseBody.put("message", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestBody loginRequestBody) {
        System.out.println("Login Request: " + loginRequestBody.getUsername() + ", " + loginRequestBody.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBody.getUsername(), loginRequestBody.getPassword())
            );
            System.out.println("Authenticated User: " + authentication.getPrincipal());
            System.out.println("Credentials: " + authentication.getCredentials());

            if (authentication.isAuthenticated()) {
                    Map<String, Object> responseBody = new HashMap<>();

                    responseBody.put("success", true);
                    responseBody.put("token", jwtService.generateToken(loginRequestBody.getUsername()));
                    return ResponseEntity.ok(responseBody);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("message", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String refreshToken) {
        System.out.println("Logout Request: " + refreshToken);
        return "Logged out successfully";
    }
}