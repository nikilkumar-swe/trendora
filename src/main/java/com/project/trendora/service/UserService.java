package com.project.trendora.service;

import com.project.trendora.dto.LoginRequest;
import com.project.trendora.dto.UserRegisterRequest;
import com.project.trendora.dto.UserRegisterResponse;
import com.project.trendora.dto.ViewProfile;

import java.security.Principal;

public interface UserService {

     UserRegisterResponse register(UserRegisterRequest userRequest);
     String login(LoginRequest request);
     ViewProfile viewProfile(Principal principal);
}
