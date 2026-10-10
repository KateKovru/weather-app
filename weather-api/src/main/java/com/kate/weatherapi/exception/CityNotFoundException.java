package com.kate.weatherapi.exception;

public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException(String city){
        super("City nor found: "+city);
    }

}
