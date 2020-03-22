package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.US_ASCII;

public enum AsterismLoader implements StarCatalogue.Loader {
    INSTANCE;

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII)){
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                while(bufferedReader.ready()){
                    String[] tabString = bufferedReader.readLine().split(", ");
                }
            }
        }
    }
}
