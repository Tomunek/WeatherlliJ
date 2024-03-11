package org.tomunek.weatherllij.OpenMeteo.JsonResponseModel;

public class OpenMeteoResponse {
    public double latitude;
    public double longitude;
    public double generationtime_ms;
    public int utc_offset_seconds;
    public String timezone;
    public String timezone_abbreviation;
    public double elevation;
    public CurrentUnits current_units;
    public Current current;
    public DailyUnits daily_units;
    public Daily daily;
}



