package org.tomunek.weatherllij;

import com.intellij.ui.JBColor;
import org.tomunek.weatherllij.OpenMeteo.WeatherGetterOpenMeteo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


final class WeatherWindowContent {
    private static WeatherWindowContent instance;

    private final WeatherGetter weatherGetter;

    private final int forecastDays = 5;

    private final JPanel contentPanel = new JPanel();

    private final JLabel currentWeatherDisplay = new JLabel();
    private final List<JLabel> weatherForecastDisplay;


    private WeatherWindowContent() {
        this.weatherGetter = new WeatherGetterOpenMeteo(51.45F, 19.27F, forecastDays);
        this.weatherForecastDisplay = new ArrayList<>(forecastDays);
        for (int i = 0; i < this.forecastDays; i++) {
            this.weatherForecastDisplay.add(new JLabel());
        }

        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.PAGE_AXIS));
        weatherPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        weatherPanel.add(createCurrentWeatherPanel());
        weatherPanel.add(createWeatherForecastPanel());
        weatherPanel.add(createButtonsPanel());

        contentPanel.setLayout(new BorderLayout(0, 20));
        contentPanel.add(weatherPanel, BorderLayout.PAGE_START);
        refreshWeatherData();
    }

    public static WeatherWindowContent getInstance() {
        if (instance == null) {
            instance = new WeatherWindowContent();
        }
        return instance;
    }

    private JPanel createCurrentWeatherPanel() {
        JPanel currentWeatherPanel = new JPanel();
        currentWeatherPanel.add(currentWeatherDisplay);
        return currentWeatherPanel;
    }

    private JPanel createWeatherForecastPanel() {
        JPanel weatherForecastPanel = new JPanel();
        weatherForecastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        weatherForecastPanel.setLayout(new BoxLayout(weatherForecastPanel, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < this.forecastDays; i++) {
            weatherForecastPanel.add(weatherForecastDisplay.get(i));
        }
        return weatherForecastPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refreshWeatherData());
        buttonsPanel.add(refresh);
        return buttonsPanel;
    }

    private void refreshWeatherData() {
        try {
            this.weatherGetter.fetchWeatherData();
        } catch (WeatherGetException e) {
            currentWeatherDisplay.setBackground(new JBColor(new Color(255, 0, 0), new Color(255, 0, 0)));
            currentWeatherDisplay.setText("COULD NOT FETCH WEATHER DATA!");
            weatherForecastDisplay.get(0).setText(e.toString());
            return;
        }

        currentWeatherDisplay.setText(this.weatherGetter.getCurrentWeather().displayAsAsciiHTML(true));
        currentWeatherDisplay.setHorizontalTextPosition(JLabel.CENTER);
        currentWeatherDisplay.setBorder(BorderFactory.createLineBorder(Color.black));

        for (int i = 0; i < forecastDays; i++) {
            weatherForecastDisplay.get(i).setText(this.weatherGetter.getForecastWeather().get(i).displayAsAsciiHTML(false));
            currentWeatherDisplay.setHorizontalTextPosition(JLabel.CENTER);
        }

        // TODO: Align boxes property
        // TODO: Add icons

    }

    public JPanel getContent() {
        return contentPanel;
    }
}
