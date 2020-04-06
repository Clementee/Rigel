package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.*;
import java.util.ArrayList;

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
                    double rarad, decrad;
                    float magnitude, colorID;
                    
                    String valInt = strings[index.HIP.ordinal()];
                    String name;

                    if (!valInt.equals("")) {
                        
                        hipparcosID = Integer.parseInt(valInt);
                    }
                    
                    else {
                        
                        hipparcosID = 0;
                    }

                    valInt = strings[index.PROPER.ordinal()];

                    if (!valInt.equals("")) {
                        
                        name = valInt;
                    }
                    else {
                        
                        valInt = strings[index.BAYER.ordinal()];

                        if (!valInt.equals("")) {
                            
                            name = valInt +" " +  strings[index.CON.ordinal()];
                        }
                        
                        else {
                            
                            name = "?" + strings[index.CON.ordinal()];
                        }
                    }

                    rarad = Double.parseDouble( strings[ index.RARAD.ordinal() ] );
                    decrad = Double.parseDouble( strings[ index.DECRAD.ordinal() ] );
                    valInt = strings[index.MAG.ordinal()];
                    
                    EquatorialCoordinates equatorialCoordinates = EquatorialCoordinates.of(rarad, decrad);

                    if (!valInt.equals("")) {
                        
                        magnitude = Float.parseFloat(valInt);
                    }
                    else {
                        
                        magnitude = 0;
                    }
                    
                    valInt = strings[index.CI.ordinal()];

                    if (!valInt.equals("")) {
                        
                        colorID = Float.parseFloat(valInt);
                    }
                    else {
                        
                        colorID = 0;
                    }

                    builder.addStar(new Star(hipparcosID, name, equatorialCoordinates, magnitude, colorID));
                }
            }
        }
    }

    /**
     * Private enum of indexes for the loader
     */
    private enum index {
        
        ID, HIP, HD, HR, GL, BF, PROPER, RA, DEC, DIST, PMRA, PMDEC,
        RV, MAG, ABSMAG, SPECT, CI, X, Y, Z, VX, VY, VZ,
        RARAD, DECRAD, PMRARAD, PMDECRAD, BAYER, FLAM, CON,
        COMP, COMP_PRIMARY, BASE, LUM, VAR, VAR_MIN, VAR_MAX;

        index() { }
    }
}
