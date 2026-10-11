package com.mc.stock_ai_analysis.utility;

import java.time.Instant;

import com.mc.stock_ai_analysis.entity.UserInfo;

public class UserFactory {
    public static UserInfo createUserInfo(String name, String email, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setRoles("user");
        userInfo.setCreatedAt(Instant.now());
        userInfo.setVipLevel(0);
        userInfo.setVipExpiredAt(null);
        return userInfo;
    }

    public static UserInfo createAdminUserInfo(String name, String email, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setRoles("user, vip");
        userInfo.setCreatedAt(Instant.now());
        userInfo.setVipLevel(0);
        userInfo.setVipExpiredAt(Instant.now().plusSeconds(30 * 24 * 60 * 60)); // Set VIP expiration to 30 days from now
        return userInfo;
    }
}
