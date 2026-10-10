package com.kate.weatherapi.service;

import com.kate.weatherapi.client.WeatherApiClient;
import com.kate.weatherapi.client.dto.ExternalForecastResponse;
import com.kate.weatherapi.client.dto.ExternalWeatherResponse;
import com.kate.weatherapi.dto.ForecastDto;
import com.kate.weatherapi.dto.ForecastItemDto;
import com.kate.weatherapi.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WeatherService {

    private final WeatherApiClient weatherApiClient;

    public WeatherService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public WeatherResponseDto getCurrentWeather(String city){

        ExternalWeatherResponse response = weatherApiClient.getCurrentWeather(city);

        return new WeatherResponseDto(
                response.name(),
                response.sys().country(),
                response.main().temp(),
                response.main().feelsLike(),
                response.main().humidity(),
                response.wind().speed(),
                response.weather().getFirst().description(),
                response.weather().getFirst().icon()
        );
    }

    public ForecastDto getForecastWeather(String city){

        ExternalForecastResponse response = weatherApiClient.getForecastWeather(city);

        List<ForecastItemDto> forecasts = response.list()
                .stream()
                .map(item -> new ForecastItemDto(
                        LocalDateTime.parse(
                                item.dateTime(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        ),
                        item.main().temp(),
                        item.main().humidity(),
                        item.weather().getFirst().description(),
                        item.weather().getFirst().icon()
                ))
                .toList();

        return new ForecastDto(
                response.city().name(),
                forecasts
        );
    }
}
