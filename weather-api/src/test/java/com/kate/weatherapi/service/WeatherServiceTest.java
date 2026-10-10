package com.kate.weatherapi.service;

import com.kate.weatherapi.client.WeatherApiClient;
import com.kate.weatherapi.client.dto.ExternalWeatherResponse;
import com.kate.weatherapi.dto.WeatherResponseDto;
import com.kate.weatherapi.exception.CityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherApiClient client;

    @InjectMocks
    private WeatherService service;

    @Test
    void shouldReturnCurrentWeather(){

        ExternalWeatherResponse apiResponse = new ExternalWeatherResponse(
                "Augsburg",
                new ExternalWeatherResponse.MainData(15.5, 14.2, 70),
                List.of(
                        new ExternalWeatherResponse.WeatherData(
                                "clear sky", "01d"
                        )
                ),
                new ExternalWeatherResponse.WindData(54.9),
                new ExternalWeatherResponse.SysData("DE")
        );

        when(client.getCurrentWeather("Augsburg")).thenReturn(apiResponse);

        WeatherResponseDto result = service.getCurrentWeather("Augsburg");

        assertEquals("Augsburg", result.city());
        assertEquals("DE", result.country());
        assertEquals(15.5, result.temperature());
        assertEquals(14.2, result.feelsLike());
        assertEquals(70, result.humidity());
        assertEquals(54.9, result.windSpeed());
        assertEquals("clear sky", result.description());
        assertEquals("01d", result.icon());

        verify(client).getCurrentWeather("Augsburg");

    }

    @Test
    void shouldThrowExceptionForInvalidCity(){

        String city="srgfs";

        when(client.getCurrentWeather(city))
                .thenThrow(new CityNotFoundException(city));

        assertThrows(CityNotFoundException.class, ()-> service.getCurrentWeather(city));

        verify(client).getCurrentWeather(city);

    }

}
