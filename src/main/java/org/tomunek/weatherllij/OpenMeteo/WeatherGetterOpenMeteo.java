package org.tomunek.weatherllij.OpenMeteo;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.tomunek.weatherllij.OpenMeteo.JsonResponseModel.OpenMeteoResponse;
import org.tomunek.weatherllij.WeatherDayData;
import org.tomunek.weatherllij.WeatherGetException;
import org.tomunek.weatherllij.WeatherGetter;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class WeatherGetterOpenMeteo implements WeatherGetter {
    private final static String METEO_API_URL = "https://api.open-meteo.com/v1/forecast";
    private String location;
    private Integer forecastDays = 7;

    private String requestURL;

    private WeatherDayData currentWeather;
    private List<WeatherDayData> forecastWeather;


    /**
     * Creates a new WeatherGetterOpenMeteo instance with the given location.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     */
    public WeatherGetterOpenMeteo(Float latitude, Float longitude, Integer forecastDays) {
        this.location = coordsToCoordsString(latitude, longitude);
        this.forecastDays = forecastDays;
        updateRequestURL();
    }

    /**
     * Sets the location to given values.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     */
    @Override
    public void setLocationByCoords(Float latitude, Float longitude) {
        this.location = coordsToCoordsString(latitude, longitude);
        updateRequestURL();
    }

    /**
     * Converts the given latitude and longitude coordinates to a string representation suitable for use in the API request URL.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @return a string representation of the given coordinates
     */
    private String coordsToCoordsString(Float latitude, Float longitude) {
        return "latitude=" + latitude + "&longitude=" + longitude;
    }

    /**
     * Updates the request URL with the current parameters.
     */
    private void updateRequestURL() {
        this.requestURL = METEO_API_URL + "?" + this.location + "&" +
                "current=weather_code,temperature_2m,precipitation&" +
                "daily=weather_code,temperature_2m_max,precipitation_sum&" +
                "timezone=auto&" +
                "forecast_days=" + this.forecastDays;
    }

    /**
     * Refreshes the weather data by making an API request to OpenWeatherMap.
     *
     * @throws WeatherGetException if an error occurs while making the request or parsing the response
     */
    @Override
    public void fetchWeatherData() throws WeatherGetException {
        try {
            // Create connection
            URL url = new URL(this.requestURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new WeatherGetException("API responded with non 200 code");
            }

            // Receive response
            StringBuilder response = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            try {
                // Parse response into WeatherDayData objects
                ObjectMapper objectMapper = new ObjectMapper();
                OpenMeteoResponse openMeteoResponse = objectMapper.readValue(response.toString(), OpenMeteoResponse.class);

                // Populate current weather
                this.currentWeather = new WeatherDayData(
                        LocalDateTime.parse(openMeteoResponse.current.time),
                        openMeteoResponse.current.weather_code,
                        openMeteoResponse.current.temperature_2m,
                        openMeteoResponse.current_units.temperature_2m,
                        openMeteoResponse.current.precipitation,
                        openMeteoResponse.current_units.precipitation);

                // Populate weather forecast
                this.forecastWeather = new ArrayList<>(this.forecastDays);
                for (int i = 0; i < this.forecastDays; i++) {
                    this.forecastWeather.add(new WeatherDayData(
                            LocalDate.parse(openMeteoResponse.daily.time.get(i)).atStartOfDay(),
                            openMeteoResponse.daily.weather_code.get(i),
                            openMeteoResponse.daily.temperature_2m_max.get(i),
                            openMeteoResponse.daily_units.temperature_2m_max,
                            openMeteoResponse.daily.precipitation_sum.get(i),
                            openMeteoResponse.daily_units.precipitation_sum));
                }
            } catch (Exception e) {
                throw new WeatherGetException("Could not parse data into objects");
            }

        } catch (Exception e) {
            throw new WeatherGetException("Error occurred while getting weather data: " + e, e);
        }
    }

    /**
     * Returns the current weather data for the location.
     *
     * @return the current weather data
     */
    @Override
    public WeatherDayData getCurrentWeather() {
        return this.currentWeather;
    }

    /**
     * Returns the weather forecast data for the location.
     *
     * @return the current weather data
     */
    @Override
    public List<WeatherDayData> getForecastWeather() {
        return this.forecastWeather;
    }
}
