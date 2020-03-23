package ch.epfl.rigel.astronomy;

import java.awt.image.Raster;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
                    for(int i = 0 ; i<tabString.length ;i++){
                        for(Star s : starList){
                            if(s.hipparcosId()== Integer.parseInt(tabString[i])){
                                asterismList.add(s);
                            }
                        }
                    }
                    builder.addAsterism(new Asterism(asterismList));

                }
            }
        }
    }
}
