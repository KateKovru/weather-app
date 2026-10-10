package com.kate.weatherapi.service;

import com.kate.weatherapi.dto.AuthResponseDto;
import com.kate.weatherapi.dto.LoginRequestDto;
import com.kate.weatherapi.dto.RegisterRequestDto;
import com.kate.weatherapi.entity.User;
import com.kate.weatherapi.repository.UserRepository;
import com.kate.weatherapi.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUser(){

        RegisterRequestDto registerRequestDto = new RegisterRequestDto(
                "test@gmail.com",
                "password123"
        );

        when(userRepository.existsByEmail("test@gmail.com"))
                .thenReturn(false);
        when(passwordEncoder.encode("password123"))
                .thenReturn("hashedPassword");

        userService.register(registerRequestDto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();

        assertEquals("test@gmail.com", savedUser.getEmail());
        assertEquals("hashedPassword", savedUser.getPassword());
        assertNotEquals("password123", savedUser.getPassword());

        verify(passwordEncoder).encode("password123");

    }

    @Test
    void shouldRejectDuplicateEmail(){

        RegisterRequestDto registerRequestDto = new RegisterRequestDto(
                "test@gmail.com",
                "password123"
        );

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                ()-> userService.register(registerRequestDto)
        );

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));

        verify(passwordEncoder, never()).encode(anyString());

    }

    @Test
    void shouldLoginUser(){

        LoginRequestDto loginRequestDto = new LoginRequestDto(
                "test@gmail.com",
                "password123"
        );

        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername("test@gmail.com"))
                .thenReturn(userDetails);

        when(jwtService.generateToken(userDetails))
                .thenReturn("fake token");

        AuthResponseDto authResponseDto = userService.login(loginRequestDto);

        assertEquals("fake token", authResponseDto.token());

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        "password123"
                )
        );

        verify(jwtService).generateToken(userDetails);

    }

}
