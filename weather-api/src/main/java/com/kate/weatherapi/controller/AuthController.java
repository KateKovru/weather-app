package com.kate.weatherapi.controller;

import com.kate.weatherapi.dto.AuthResponseDto;
import com.kate.weatherapi.dto.LoginRequestDto;
import com.kate.weatherapi.dto.RegisterRequestDto;
import com.kate.weatherapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequestDto request){

        userService.register(request);
    }
    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto request){

        return userService.login(request);
    }
}
