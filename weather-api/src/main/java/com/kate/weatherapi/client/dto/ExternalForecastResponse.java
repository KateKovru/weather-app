package com.kate.weatherapi.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalForecastResponse(

        CityData city,
        List<ForecastItem> list

) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CityData(
            String name
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ForecastItem(
            @JsonProperty("dt_txt")
            String dateTime,
            Main main,
            List<Weather> weather
    ){

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Main(
            double temp,
            int humidity
        ){}

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Weather(
            String description,
            String icon
        ){}

    }


}
