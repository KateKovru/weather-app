package com.kate.weatherapi.service;

import com.kate.weatherapi.dto.AuthResponseDto;
import com.kate.weatherapi.dto.LoginRequestDto;
import com.kate.weatherapi.dto.RegisterRequestDto;
import com.kate.weatherapi.dto.UserResponseDto;
import com.kate.weatherapi.entity.User;
import com.kate.weatherapi.repository.UserRepository;
import com.kate.weatherapi.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequestDto request){

        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = new User();

        user.setEmail(request.email());
        user.setPassword(encodedPassword);

        userRepository.save(user);

    }

    public AuthResponseDto login(LoginRequestDto request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDto(token);
    }

    public UserResponseDto getCurrentUser(String email){

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
                );

        return new UserResponseDto(user.getId(), user.getEmail());
    }

}
