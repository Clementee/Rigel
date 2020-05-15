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

public enum ConstellationLoader implements StarCatalogue.Loader {

    INSTANCE;

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            Map<String, String> bayerToFullNameMap = new HashMap<>();

            String line = bufferedReader.readLine();
            while (line != null) {
                String[] inputLineTab = line.split(",");
                bayerToFullNameMap.put(inputLineTab[0], inputLineTab[1]);
                line = bufferedReader.readLine();
            }
            List<Asterism> asterisms = builder.asterisms();
            for (Asterism ast : asterisms) {
                Objects.requireNonNull(ast.stars());
                String str = ast.stars().get(0).bayer();
                if (!str.equals("?"))
                    builder.addConstellation(new Constellation(bayerToFullNameMap.get(str), ast));
            }
        }
    }
}
