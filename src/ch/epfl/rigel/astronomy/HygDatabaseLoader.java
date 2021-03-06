package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.*;

import static java.nio.charset.StandardCharsets.US_ASCII;


/**
 * A loader of stars from a database
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum HygDatabaseLoader implements StarCatalogue.Loader {

    INSTANCE;

    /**
     * HygDatabaseLoader method loading the stars from an inputStream into a builder
     *
     * @param inputStream (InputStream) : gives the inputStream from where we take the stars
     * @param builder     (StarCatalogue.builder) : gives the builder of the star catalogue we are initializing
     * @throws IOException : throws IO Exception when the method fails and called
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            bufferedReader.readLine();
            String inputLine = bufferedReader.readLine();
            while (inputLine != null) {
                String[] lineTab = inputLine.split(",");

                int hipparcosID = Integer.parseInt(validOrDefault(lineTab[Index.HIP.ordinal()], lineTab[Index.HIP.ordinal()], "0"));
                String name = validOrDefault(lineTab[Index.PROPER.ordinal()], lineTab[Index.PROPER.ordinal()],
                        validOrDefault(lineTab[Index.BAYER.ordinal()], lineTab[Index.BAYER.ordinal()]
                                + " " + lineTab[Index.CON.ordinal()], "? " + lineTab[Index.CON.ordinal()]));
                double rarad = Double.parseDouble(lineTab[Index.RARAD.ordinal()]);
                double decrad = Double.parseDouble(lineTab[Index.DECRAD.ordinal()]);
                EquatorialCoordinates equatorialCoordinates = EquatorialCoordinates.of(rarad, decrad);
                float magnitude = Float.parseFloat(validOrDefault(lineTab[Index.MAG.ordinal()], lineTab[Index.MAG.ordinal()], "0"));
                float colorID = Float.parseFloat(validOrDefault(lineTab[Index.CI.ordinal()], lineTab[Index.CI.ordinal()], "0"));
                String bayer = !(lineTab[Index.CON.ordinal()]).isEmpty() ? lineTab[Index.CON.ordinal()] : "?";

                builder.addStar(new Star(hipparcosID, name, equatorialCoordinates, magnitude, colorID, bayer));
                inputLine = bufferedReader.readLine();
            }

        }
    }

    /**
     * Private method checking if the tested value is valid else return a default value
     * 
     * @param toTest (String) : gives the string to test
     * @param validValue (String) : gives the string of reference, the one we use to compare the tested value
     * @param defaultValue (String) : gives the default value when the tested value is invalid
     * @return (String) :  return the tested value if valid else the default value
     */
    private static String validOrDefault(String toTest, String validValue, String defaultValue) {
        return !toTest.isEmpty() ? validValue : defaultValue;
    }

    /**
     * Private enum of indexes for the loader
     */
    private enum Index {

        ID, HIP, HD, HR, GL, BF, PROPER, RA, DEC, DIST, PMRA, PMDEC,
        RV, MAG, ABSMAG, SPECT, CI, X, Y, Z, VX, VY, VZ,
        RARAD, DECRAD, PMRARAD, PMDECRAD, BAYER, FLAM, CON,
        COMP, COMP_PRIMARY, BASE, LUM, VAR, VAR_MIN, VAR_MAX;

        Index() {
        }
    }
}
