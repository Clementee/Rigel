package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.US_ASCII;

public enum AsterismLoader implements StarCatalogue.Loader {
    INSTANCE;

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII)){
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                List<Star> starList = builder.stars();
                while(bufferedReader.ready()){
                    String[] tabString = bufferedReader.readLine().split(", ");
                    List<Star> asterismList = new LinkedList<>();
                    Map<Integer,Star> asterismListed = new HashMap<>();

                    for(Star starInter : starList){
                        asterismListed.put(starInter.hipparcosId(),starInter);
                    }
                    for(int i = 0 ; i<tabString.length ;i++){
                            if(asterismListed.containsKey(Integer.parseInt(tabString[i]))){
                                asterismList.add(asterismListed.get(Integer.parseInt(tabString[i])));
                            }
                        }
                    builder.addAsterism(new Asterism(asterismList));
                }
            }
        }
    }
}
