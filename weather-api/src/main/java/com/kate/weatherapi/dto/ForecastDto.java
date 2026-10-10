package com.kate.weatherapi.dto;

import java.util.List;

public record ForecastDto(
        String city,
        List<ForecastItemDto> forecasts
) {
}

