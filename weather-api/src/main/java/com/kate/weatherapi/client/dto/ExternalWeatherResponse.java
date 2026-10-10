package com.kate.weatherapi.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalWeatherResponse (
        String name,
        MainData main,
        List<WeatherData> weather,
        WindData wind,
        SysData sys

){

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MainData(
            double temp,
            @JsonProperty("feels_like")
            double feelsLike,
            int humidity
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WeatherData(
            String description,
            String icon
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WindData(
            double speed
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SysData(
            String country
    ){}

}
