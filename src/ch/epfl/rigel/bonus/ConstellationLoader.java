package ch.epfl.rigel.bonus;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.astronomy.StarCatalogue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class ConstellationLoader implements StarCatalogue.Loader {

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            List<Star> starList = builder.stars();

            Map<Integer, Star> constellationsListed = new HashMap<>();

            for (Star starInter : starList) {
                constellationsListed.put(starInter.hipparcosId(), starInter);
            }

            String line = bufferedReader.readLine();
            while (line != null) {
                String[] inputLineTab = line.split(",");

                List<Star> starsList = new LinkedList<>();

                String name = "";

                for (String string : inputLineTab) {
                    for (int k = 0; k < inputLineTab.length; k++) {

                        if (k == 0) {
                            name = string;
                        } else if (Integer.parseInt(string) != 0) {

                            starsList.
                                    add(constellationsListed.get(Integer.parseInt(string)));
                        }
                    }

                    builder.addConstellation(new Constellation(name,new Asterism(starsList)));
                    line = bufferedReader.readLine();

                }
            }
        }
    }
}