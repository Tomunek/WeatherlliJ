package org.tomunek.weatherllij.OpenMeteo.JsonResponseModel;

import java.util.List;

public class Daily {
    public List<String> time;
    public List<Integer> weather_code;
    public List<Double> temperature_2m_max;
    public List<Double> precipitation_sum;
}
