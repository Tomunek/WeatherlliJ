package org.tomunek.weatherllij;

import java.util.List;

public interface WeatherGetter {
    void setLocationByCoords(Float latitude, Float longitude);

    void fetchWeatherData() throws WeatherGetException;

    WeatherDayData getCurrentWeather();

    List<WeatherDayData> getForecastWeather();
}
