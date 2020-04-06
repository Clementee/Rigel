package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * A loader of asterism from a database
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum AsterismLoader implements StarCatalogue.Loader {

    INSTANCE;

    /**
     * AsterismLoader method loading the asterism from an inputStream into a builder
     *
     * @param inputStream   (InputStream) : gives the inputStream from where we take the asterism
     * @param builder       (StarCatalogue.builder) : gives the builder of the star catalogue we are initializing
     * @throws IOException : throws the IOException
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream , US_ASCII)){
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                List<Star> starList = builder.stars();
                List<String[]> inputList = new ArrayList<>();
                
                Map<Integer,Star> asterismListed = new HashMap<>();

                while(bufferedReader.ready()){
                    
                    String[] inputLineTab = bufferedReader
                            .readLine()
                            .split(",");
                    
                    inputList.add(inputLineTab);
                }

                for(Star starInter : starList){
                
                    asterismListed.put(starInter.hipparcosId() , starInter);
                }
                
                for (String[] hipparsList : inputList) {

                    List<Star> asterismList = new LinkedList<>();

                    for (String string : hipparsList) {

                        if(Integer.parseInt(string) != 0){

                            asterismList.
                                    add(asterismListed.get(Integer.parseInt(string)));
                        }
                    }

                    builder.addAsterism(new Asterism(asterismList));
                }
            }
        }
    }
}
