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
        weatherPanel.setLayout(new GridBagLayout());
        weatherPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        GridBagConstraints gridBagConstraint = new GridBagConstraints();
        gridBagConstraint.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraint.gridx = 0;
        gridBagConstraint.gridy = 0;
        weatherPanel.add(createCurrentWeatherPanel(), gridBagConstraint);
        gridBagConstraint.gridy = 1;
        weatherPanel.add(createWeatherForecastPanel(), gridBagConstraint);
        gridBagConstraint.gridy = 2;
        weatherPanel.add(createButtonsPanel(), gridBagConstraint);

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
        currentWeatherPanel.setBorder(BorderFactory.createLineBorder(JBColor.BLACK));
        currentWeatherPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        currentWeatherPanel.add(currentWeatherDisplay);
        return currentWeatherPanel;
    }

    private JPanel createWeatherForecastPanel() {
        JPanel weatherForecastPanel = new JPanel();
        weatherForecastPanel.setBorder(BorderFactory.createLineBorder(JBColor.BLACK));
        weatherForecastPanel.setLayout(new GridLayout(5, 1));
        weatherForecastPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
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
        currentWeatherDisplay.setIcon(
                WeatherIcons.getImageIconFromWMOCode(this.weatherGetter.getCurrentWeather().getWmoCode()));

        for (int i = 0; i < forecastDays; i++) {
            weatherForecastDisplay.get(i).setText(
                    this.weatherGetter.getForecastWeather().get(i).displayAsAsciiHTML(false));
            weatherForecastDisplay.get(i).setIcon(
                    WeatherIcons.getImageIconFromWMOCode(this.weatherGetter.getForecastWeather().get(i).getWmoCode()));
        }

        // TODO: Align boxes property
    }

    public JPanel getContent() {
        return contentPanel;
    }
}
