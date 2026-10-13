package com.project.trendora.controller;

import com.project.trendora.dto.LoginRequest;
import com.project.trendora.dto.UserRegisterRequest;
import com.project.trendora.dto.UserRegisterResponse;
import com.project.trendora.dto.ViewProfile;
import com.project.trendora.service.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User APIs",description = "Operations related to user")
@Slf4j
public class UserController {
    private final UserServiceImpl userService;
    public UserController(UserServiceImpl userService){
        this.userService=userService;
    }

    @Operation(summary = "Register the user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "user registered successfully"),
            @ApiResponse(responseCode ="400", description = "Bad request,user not registered")
    })
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request){
        log.info("user registration request received");
        UserRegisterResponse registeredUser=userService.register(request);
        return  ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Login the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "user logged in successfully"),
            @ApiResponse(responseCode ="401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){
        log.info("User login request received");
        String user=userService.login(request);
        log.info("user logged in successfully");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/view/profile")
    public ResponseEntity<ViewProfile> viewProfile(Principal principal){
       log.info("view logged-in user profile request received");
        ViewProfile profile=userService.viewProfile(principal);
        return ResponseEntity.ok(profile);
    }

}
