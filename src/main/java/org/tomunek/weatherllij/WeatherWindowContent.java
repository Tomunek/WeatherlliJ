package org.tomunek.weatherllij;

import javax.swing.*;
import java.awt.*;


final class WeatherWindowContent {
    private static WeatherWindowContent instance;

    private final JPanel contentPanel = new JPanel();

    private final JLabel counterDisplay = new JLabel();
    private final JLabel counterDisplay2 = new JLabel();

    private Integer counter = 0;

    private WeatherWindowContent() {
        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.PAGE_AXIS));
        weatherPanel.add(createCurrentWeatherPanel());
        weatherPanel.add(createCurrentWeatherPanel2());
        weatherPanel.add(createButtonsPanel());

        contentPanel.setLayout(new BorderLayout(0, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
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

    private JPanel createCurrentWeatherPanel2() {
        JPanel currentWeatherPanel = new JPanel();
        currentWeatherPanel.add(counterDisplay2);
        return currentWeatherPanel;
    }


    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refreshWeatherData());
        buttonsPanel.add(refresh);
        return buttonsPanel;
    }

    private void refreshWeatherData() {
        this.counter++;
        counterDisplay.setText("Display1: " + this.counter);
        counterDisplay2.setText("Display2: " + this.counter);
    }


    public JPanel getContent() {
        return contentPanel;
    }
}
