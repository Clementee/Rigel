package ch.epfl.rigel.gui;

import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ch.epfl.rigel.Preconditions.checkInInterval;

public class BlackBodyColor {

    private static final String COLOR_CATALOGUE_NAME = "/bbr_color.txt";
    private final static Map<Integer, Color> colorList = load();

    private BlackBodyColor() throws UnsupportedOperationException {
    }

    public static Color colorForTemperature(double temperatureKelv) {
        checkInInterval(ClosedInterval.of(1000, 40000), temperatureKelv);
        final int actTemperatureKelv = (int) Math.round(temperatureKelv / 100) * 100;
        return colorList.get(actTemperatureKelv);
    }

    private static Map<Integer, Color> load() {

        Map<Integer, Color> colorMap = new HashMap<>();

        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                BlackBodyColor.class.getResourceAsStream(COLOR_CATALOGUE_NAME), StandardCharsets.US_ASCII))) {
            while (bufferedReader.ready()) {
                if (!(bufferedReader.readLine().startsWith("#") || bufferedReader.readLine().substring(10, 15).equals(" 2deg"))) {
                    String colorLine = bufferedReader.readLine().substring(80, 87);
                    Integer temperatureLine = Integer.valueOf(bufferedReader.readLine().substring(1, 6));
                    colorMap.put(temperatureLine, Color.web(colorLine));
                }
            }
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return colorMap;
    }
}
