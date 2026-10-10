package com.kate.weatherapi.controller;

import com.kate.weatherapi.dto.ForecastDto;
import com.kate.weatherapi.dto.WeatherResponseDto;
import com.kate.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "http://localhost:5173")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public WeatherResponseDto getCurrentWeather(@RequestParam String city){

        return weatherService.getCurrentWeather(city);
    }

    @GetMapping("/forecast")
    public ForecastDto getForecastWeather(@RequestParam String city){

        return weatherService.getForecastWeather(city);
    }
}
