package com.mc.stock_ai_analysis.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mc.stock_ai_analysis.entity.UserInfo;
import com.mc.stock_ai_analysis.repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Autowired
    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        
        UserInfo user = repository.findByEmail(username)
            .orElseThrow(() ->
                    new UsernameNotFoundException(
                            "User not found with email: " + username));
        
        return new UserInfoDetails(user);
    }

    public Optional<String> loadUserIdByEmail(String email) {
        Optional<UserInfo> userOptional = repository.findByEmail(email);
        if (userOptional.isPresent()) {
            return Optional.of(userOptional.get().getId());
        } else {
            return Optional.empty();
        }
    }

    // Add any additional methods for registering or managing users
    public String addUser(UserInfo userInfo) {
        // Encrypt password before saving
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        UserInfo savedUser = repository.save(userInfo);
        return "Added user with ID: " + savedUser.getId();
    }
}