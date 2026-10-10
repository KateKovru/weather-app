package com.kate.weatherapi.service;

import com.kate.weatherapi.dto.FavoriteLocationDto;
import com.kate.weatherapi.entity.FavoriteLocation;
import com.kate.weatherapi.entity.User;
import com.kate.weatherapi.repository.FavoriteRepository;
import com.kate.weatherapi.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    @AfterAll
    static void deleteSecurityContext(){
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAddFavorite(){

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null
                )
        );

        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        FavoriteLocationDto request =
                new FavoriteLocationDto(null, "Munich", "DE");

        when(favoriteRepository.save(any(FavoriteLocation.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        FavoriteLocationDto result = favoriteService.addFavorite(request);

        assertEquals("Munich", result.city());
        assertEquals("DE", result.country());

        ArgumentCaptor<FavoriteLocation> captor =
                ArgumentCaptor.forClass(FavoriteLocation.class);

        verify(favoriteRepository).save(captor.capture());

        FavoriteLocation saved = captor.getValue();

        assertEquals("Munich", saved.getCity());
        assertEquals("DE", saved.getCountry());
        assertSame(user, saved.getUser());

    }

    @Test
    void shouldNotDeleteOtherUsersFavorite(){

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null
                )
        );

        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(favoriteRepository.findByIdAndUser(5,user))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                ()->favoriteService.deleteFavorite(5));

        verify(favoriteRepository, never())
                .delete(any(FavoriteLocation.class));

    }
}
