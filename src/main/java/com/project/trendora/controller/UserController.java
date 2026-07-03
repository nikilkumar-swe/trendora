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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User APIs",description = "Operations related to user")
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
        return ResponseEntity.ok(userService.register(request));
    }

    @Operation(summary = "Login the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "user logged in successfully"),
            @ApiResponse(responseCode ="401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/view/profile")
    public ResponseEntity<ViewProfile> viewProfile(Principal principal){
        return ResponseEntity.ok(userService.viewProfile(principal));
    }

}
