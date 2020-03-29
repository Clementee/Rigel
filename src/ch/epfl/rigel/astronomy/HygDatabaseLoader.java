package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import static java.nio.charset.StandardCharsets.US_ASCII;

public enum HygDatabaseLoader implements StarCatalogue.Loader{
    INSTANCE;

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        ArrayList<String[]> inputTab = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII)) {
            try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String inputLine = bufferedReader.readLine();
                while (bufferedReader.ready()) {
                    inputLine = bufferedReader.readLine();
                    inputTab.add(inputLine.split(","));
                }

                for (String[] strings : inputTab) {
                    int hipparcosID;
                    String name;
                    double rarad, decrad;
                    float magnitude, colorID;
                    String[] tab = strings;
                    String valInt = tab[index.HIP.ordinal()];
                    if (!valInt.equals("")) {
                        hipparcosID = Integer.parseInt(valInt);
                    } else {
                        hipparcosID = 0;
                    }
                    valInt = tab[index.PROPER.ordinal()];
                    if (!valInt.equals("")) {
                        name = valInt;
                    } else {
                        valInt = tab[index.BAYER.ordinal()];
                        if (!valInt.equals("")) {
                            name = valInt + tab[index.CON.ordinal()];
                        } else {
                            name = "?" + tab[index.CON.ordinal()];
                        }
                    }
                    rarad = Double.parseDouble(tab[index.RARAD.ordinal()]);
                    decrad = Double.parseDouble(tab[index.DECRAD.ordinal()]);
                    valInt = tab[index.MAG.ordinal()];
                    EquatorialCoordinates equatorialCoordinates = EquatorialCoordinates.of(rarad, decrad);
                    if (!valInt.equals("")) {
                        magnitude = Float.parseFloat(valInt);
                    } else {
                        magnitude = 0;
                    }
                    valInt = tab[index.CI.ordinal()];
                    if (!valInt.equals("")) {
                        colorID = Float.parseFloat(valInt);
                    } else {
                        colorID = 0;
                    }
                    builder.addStar(new Star(hipparcosID, name, equatorialCoordinates, magnitude, colorID));
                }
                }
            }
        }


    private enum index{
        ID, HIP, HD, HR, GL, BF, PROPER, RA, DEC, DIST, PMRA, PMDEC,
        RV, MAG, ABSMAG, SPECT, CI, X, Y, Z, VX, VY, VZ,
        RARAD, DECRAD, PMRARAD, PMDECRAD, BAYER, FLAM, CON,
        COMP, COMP_PRIMARY, BASE, LUM, VAR, VAR_MIN, VAR_MAX;

        index(){}
    }
}
