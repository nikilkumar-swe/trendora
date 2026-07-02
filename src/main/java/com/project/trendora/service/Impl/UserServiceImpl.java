package com.project.trendora.service.Impl;

import com.project.trendora.Repository.UserRepository;
import com.project.trendora.dto.LoginRequest;
import com.project.trendora.dto.UserRegisterRequest;
import com.project.trendora.dto.UserRegisterResponse;
import com.project.trendora.exceptions.UserAlreadyExists;
import com.project.trendora.models.User;
import com.project.trendora.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRequest) {
        logger.info("Registering user {}", userRequest.getUsername());
        if (userRepository.existsByUsername(userRequest.getUsername())){
            logger.warn("User with username {} already exist",userRequest.getUsername());
            throw new UserAlreadyExists("user is already exist");
        }
        User user=new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles("USER");

        User savedUser=userRepository.save(user);
        logger.info("User registered successfully with id {}",savedUser.getUserId());
        return mapToResponse(savedUser);
    }

    private UserRegisterResponse mapToResponse(User savedUser){
        UserRegisterResponse response=new UserRegisterResponse();
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;
    }

    //login the user
    @Override
    public String login(LoginRequest request){
        try {
            Authentication authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
            );
            return "User logged In successfully";
        }catch(RuntimeException ex){
            return ex.getMessage();
        }

    }


}
