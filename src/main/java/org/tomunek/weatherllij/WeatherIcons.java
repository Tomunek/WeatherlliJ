package org.tomunek.weatherllij;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Objects;

public class WeatherIcons {
    private static final ImageIcon sun;
    private static final ImageIcon sun_cloud;
    private static final ImageIcon cloud;
    private static final ImageIcon fog;
    private static final ImageIcon rain;
    private static final ImageIcon snow;
    private static final ImageIcon thunder;

    public static final ImageIcon error;

    static {
        try {
            sun = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/sun.png"))));
            sun_cloud = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/sun_cloud.png"))));
            cloud = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/cloud.png"))));
            fog = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/fog.png"))));
            rain = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/rain.png"))));
            snow = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/snow.png"))));
            thunder = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/thunder.png"))));
            error = new ImageIcon(
                    ImageIO.read(
                            Objects.requireNonNull(
                                    WeatherIcons.class.getResourceAsStream(
                                            "/icons/weatherConditionIcons/error.png"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static ImageIcon getImageIconFromWMOCode(int WMOCode) {
        if (WMOCode == 0) {
            return sun;
        } else if (WMOCode >= 1 && WMOCode <= 3) {
            return sun_cloud;
        } else if (WMOCode >= 4 && WMOCode <= 10) {
            return cloud;
        } else if (WMOCode >= 11 && WMOCode <= 12) {
            return fog;
        } else if (WMOCode == 13 || WMOCode == 17) {
            return thunder;
        } else if (WMOCode >= 14 && WMOCode <= 16) {
            return rain;
        } else if (WMOCode >= 36 && WMOCode <= 39) {
            return snow;
        } else if (WMOCode >= 40 && WMOCode <= 49) {
            return fog;
        } else if (WMOCode >= 50 && WMOCode <= 69) {
            return rain;
        } else if (WMOCode >= 70 && WMOCode <= 79) {
            return snow;
        } else if (WMOCode >= 80 && WMOCode <= 82) {
            return rain;
        } else if (WMOCode >= 82 && WMOCode <= 90) {
            return snow;
        } else if (WMOCode >= 92 && WMOCode <= 94) {
            return rain;
        } else if (WMOCode >= 95 && WMOCode <= 99) {
            return thunder;
        }
        return error;
    }
}
