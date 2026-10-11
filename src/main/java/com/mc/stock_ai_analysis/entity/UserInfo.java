package com.mc.stock_ai_analysis.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserInfo {
    
    @Id 
    private String id;
    private String name;
    private String email;
    private String password;
    private String roles;
    private Instant createdAt;
    private Instant vipExpiredAt;
    private int vipLevel;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public void setVipExpiredAt(Instant vipExpiredAt) {
        this.vipExpiredAt = vipExpiredAt;
    }
}