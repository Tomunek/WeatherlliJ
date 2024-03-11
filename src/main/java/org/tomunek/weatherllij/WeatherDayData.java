package org.tomunek.weatherllij;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherDayData {
    private final LocalDateTime time;

    private final Integer wmoCode;

    private final Double temperature;
    private final String temperatureUnit;

    private final Double precipitation;
    private final String precipitationUnit;

    public WeatherDayData(LocalDateTime time, Integer wmoCode, Double temperature, String temperatureUnit, Double precipitation, String precipitationUnit) {
        this.time = time;
        this.wmoCode = wmoCode;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
        this.precipitation = precipitation;
        this.precipitationUnit = precipitationUnit;
    }

    @Override
    public String toString() {
        return "WeatherDayData{" +
                time +
                ", wmoCode=" + wmoCode +
                ", " + temperature +
                temperatureUnit + '\'' +
                ", precipitation=" + precipitation +
                precipitationUnit + '\'' +
                '}';
    }

    public String displayAsAsciiHTML(boolean time) {
        DateTimeFormatter dateTimeFormatter;
        if (time) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        } else {
            dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        }
        return "<html>" + this.time.format(dateTimeFormatter) + "<br/>" +
                "\uD83C\uDF21\uFE0F" + this.temperature + this.temperatureUnit + "<br/>" +
                "\u2614" + this.precipitation + this.precipitationUnit + "</html>";
    }
}
