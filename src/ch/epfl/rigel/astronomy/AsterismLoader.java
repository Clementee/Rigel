package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.nio.charset.StandardCharsets.US_ASCII;

public enum AsterismLoader implements StarCatalogue.Loader {
    INSTANCE;

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII)){
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                List<Star> starList = builder.stars();
                List<String[]> inputList = new ArrayList<>();
                while(bufferedReader.ready()){
                    String[] inputLineTab = bufferedReader.readLine().split(",");
                    inputList.add(inputLineTab);}

                Map<Integer,Star> asterismListed = new HashMap<>();

                for(Star starInter : starList){
                    asterismListed.put(starInter.hipparcosId(),starInter);
                }
                for (String[] hipparsList : inputList) {
                    List<Star> asterismList = new LinkedList<>();
                    for (String string : hipparsList) {
                        asterismList.add(asterismListed.get(Integer.parseInt(string)));
                    }
                    builder.addAsterism(new Asterism(asterismList));
                }
                }
            }
        }
    }

