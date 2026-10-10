package com.kate.weatherapi.controller;

import com.kate.weatherapi.dto.FavoriteLocationDto;
import com.kate.weatherapi.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/me/favorites")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @GetMapping
    public List<FavoriteLocationDto> getAllFavorites(){

        return favoriteService.getAllFavorites();
    }

    @PostMapping
    public FavoriteLocationDto addFavorite(@RequestBody FavoriteLocationDto dto){

        return favoriteService.addFavorite(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable("id") int id){

        favoriteService.deleteFavorite(id);

    }

}
