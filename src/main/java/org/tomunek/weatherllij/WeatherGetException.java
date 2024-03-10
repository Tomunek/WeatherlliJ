package org.tomunek.weatherllij;

public class WeatherGetException extends Exception {
    public WeatherGetException(String message) {
        super(message);
    }

    public WeatherGetException(String message, Throwable cause) {
        super(message, cause);
    }
}
