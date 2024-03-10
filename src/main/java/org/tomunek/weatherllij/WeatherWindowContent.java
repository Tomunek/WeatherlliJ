package org.tomunek.weatherllij;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;


final class WeatherWindowContent {
    private static WeatherWindowContent instance;

    private final WeatherGetter weatherGetter;

    private final JPanel contentPanel = new JPanel();

    private final JLabel counterDisplay = new JLabel();
    private final JLabel counterDisplay2 = new JLabel();

    private WeatherWindowContent() {
        this.weatherGetter = new WeatherGetterOpenMeteo(51.45F, 19.27F, 5);

        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.PAGE_AXIS));
        weatherPanel.add(createCurrentWeatherPanel());
        weatherPanel.add(createWeatherForecastPanel());
        weatherPanel.add(createButtonsPanel());

        contentPanel.setLayout(new BorderLayout(0, 20));
        //contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
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
        currentWeatherPanel.add(counterDisplay);
        return currentWeatherPanel;
    }

    private JPanel createWeatherForecastPanel() {
        JPanel weatherForecastPanel = new JPanel();
        weatherForecastPanel.add(counterDisplay2);
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
            counterDisplay.setText("COULD NOT FETCH WEATHER DATA!");
            counterDisplay.setBackground(new JBColor(new Color(255, 0, 0), new Color(255, 0, 0)));
            counterDisplay2.setText(e.toString());
            return;
        }

        counterDisplay.setText("Received data");
        counterDisplay2.setText(this.weatherGetter.getDebug());

        // TODO: fill all panels and labels with data from getCurrentWeather() and getForecastWeather()
        // TODO: create and fill top panel with current weather
        // TODO: create and fill middle panel with weather forecast

    }

    public JPanel getContent() {
        return contentPanel;
    }
}
