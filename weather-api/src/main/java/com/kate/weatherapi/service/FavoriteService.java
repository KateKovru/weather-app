package com.kate.weatherapi.service;

import com.kate.weatherapi.dto.FavoriteLocationDto;
import com.kate.weatherapi.entity.FavoriteLocation;
import com.kate.weatherapi.entity.User;
import com.kate.weatherapi.repository.FavoriteRepository;
import com.kate.weatherapi.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    public List<FavoriteLocationDto> getAllFavorites(){

        User user = getCurrentUser();

        return favoriteRepository.findAllByUser(user)
                .stream()
                .map(this::toDto)
                .toList();

    }

    public FavoriteLocationDto addFavorite(FavoriteLocationDto newLocation){

        User user = getCurrentUser();

        FavoriteLocation fav = FavoriteLocation.builder()
                                                .city(newLocation.city())
                                                .country(newLocation.country())
                                                .user(user)
                                                .build();

        FavoriteLocation saved = favoriteRepository.save(fav);

        return toDto(saved);
    }

    public void deleteFavorite(int id){

        User user = getCurrentUser();

        FavoriteLocation fav = favoriteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new UsernameNotFoundException("Location not found"));

        favoriteRepository.delete(fav);
    }

    private User getCurrentUser(){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    private FavoriteLocationDto toDto(FavoriteLocation favorite){

        return new FavoriteLocationDto(
                favorite.getId(),
                favorite.getCity(),
                favorite.getCountry()
        );
    }
}
