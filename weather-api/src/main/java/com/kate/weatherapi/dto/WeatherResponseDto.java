package com.kate.weatherapi.dto;

public record WeatherResponseDto(
        String city,
        String country,

        double temperature,
        double feelsLike,

        int humidity,
        double windSpeed,

        String description,
        String icon

) {

}
