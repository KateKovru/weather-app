package com.kate.weatherapi.dto;

public record LoginRequestDto(
        String email,
        String password
) {
}
