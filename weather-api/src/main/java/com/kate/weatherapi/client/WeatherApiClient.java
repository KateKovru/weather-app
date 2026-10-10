package com.kate.weatherapi.client;

import com.kate.weatherapi.client.dto.ExternalForecastResponse;
import com.kate.weatherapi.client.dto.ExternalWeatherResponse;
import com.kate.weatherapi.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WeatherApiClient {

    private final RestClient weatherRestClient;
    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherApiClient(RestClient weatherRestClient) {
        this.weatherRestClient = weatherRestClient;
    }

    public ExternalWeatherResponse getCurrentWeather(String city){
        return weatherRestClient.get()
                                .uri(uriBuilder ->
                                        uriBuilder.path("/data/2.5/weather")
                                                .queryParam("q", city)
                                                .queryParam("appid", apiKey)
                                                .queryParam("units", "metric")
                                                .build())
                                .retrieve()
                                .onStatus(HttpStatus.NOT_FOUND::equals,
                                         (request, response) ->{
                                            throw new CityNotFoundException(city);
                                         }
                                         )
                                .body(ExternalWeatherResponse.class);
    }

    public ExternalForecastResponse getForecastWeather(String city){
        return weatherRestClient.get()
                                .uri(uriBuilder ->
                                        uriBuilder.path("/data/2.5/forecast")
                                                .queryParam("q", city)
                                                .queryParam("appid", apiKey)
                                                .queryParam("units", "metric")
                                                .build())
                                .retrieve()
                                .onStatus(HttpStatus.NOT_FOUND::equals,
                                        (request, response) -> {
                                            throw new CityNotFoundException(city);
                                        }
                                        )
                                .body(ExternalForecastResponse.class);
    }

}
