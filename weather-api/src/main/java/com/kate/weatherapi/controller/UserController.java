package com.kate.weatherapi.controller;

import com.kate.weatherapi.dto.UserResponseDto;
import com.kate.weatherapi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponseDto getCurrentUser(Authentication authentication){

        String username = authentication.getName();

        return userService.getCurrentUser(username);
    }


}
