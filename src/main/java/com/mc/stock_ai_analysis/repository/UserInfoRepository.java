package com.mc.stock_ai_analysis.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mc.stock_ai_analysis.entity.UserInfo;

public interface UserInfoRepository extends MongoRepository<UserInfo,Integer> {
    Optional<UserInfo> findByEmail(String email);
}
