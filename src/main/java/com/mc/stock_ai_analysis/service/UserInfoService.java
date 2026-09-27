package com.mc.stock_ai_analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mc.stock_ai_analysis.entity.UserInfo;

@Service
public class UserInfoService implements UserDetailsService {

    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserInfo user = new UserInfo();

        user.setEmail(username);
        user.setPassword(encoder.encode("password")); // In a real application, retrieve the password from the database
        user.setRoles("ROLE_USER"); // Assign a default role

        return new UserInfoDetails(user);
    }

    // Add any additional methods for registering or managing users
    public String addUser(UserInfo userInfo) {
        // Encrypt password before saving
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        // repository.save(userInfo);
        return "User added successfully!";
    }
}