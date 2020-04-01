package ch.epfl.rigel.gui;

import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.*;

import static ch.epfl.rigel.Preconditions.checkInInterval;

public class BlackBodyColor {

    private static final String COLOR_CATALOGUE_NAME = "/bbr_color.txt";
    private Map<Integer, Color> colorList = load();


    public Color colorForTemperature(double temperatureKelv) {
        checkInInterval(ClosedInterval.of(1000, 40000), temperatureKelv);
        int actTemperatureKelv = (int) Math.round(temperatureKelv/100)*100;
        return colorList.get(actTemperatureKelv);
    }

    private Map<Integer,Color> load(){
        try (FileReader inputStreamReader = new FileReader(COLOR_CATALOGUE_NAME)){
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                while(bufferedReader.ready()){
                    if(!(bufferedReader.readLine().startsWith("#")|| bufferedReader.readLine().substring(10, 15).equals(" 2deg"))){
                        String colorLine = bufferedReader.readLine().substring(80, 87);
                        Integer temperatureLine = Integer.valueOf(bufferedReader.readLine().substring(1,6));
                        colorList.put(temperatureLine,Color.web(colorLine));
                    }
                }
            }
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return colorList;
    }
}
