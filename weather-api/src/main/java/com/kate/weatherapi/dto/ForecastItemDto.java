package com.kate.weatherapi.dto;

import java.time.LocalDateTime;

public record ForecastItemDto(

        LocalDateTime time,
        double temperature,
        int humidity,
        String description,
        String icon

){}
