package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.bonus.Constellation;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static ch.epfl.rigel.Preconditions.checkArgument;

/**
 * A catalogue of stars
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class StarCatalogue {

    private final List<Star> starList;
    private final Map<Asterism, List<Integer>> asterismMap;
    private final Map<Constellation, List<Integer>> constellationMap;

    /**
     * StarCatalogue public constructor creating and initializing a catalogue of stars
     *
     * @param stars     (List<Star>) : gives the list of stars
     * @param asterisms (List<Asterism>) : gives the list of asterisms
     */
    public StarCatalogue(List<Star> stars, List<Asterism> asterisms, List<Constellation> constellations) {
        
        starList = List.copyOf(stars);

        Map<Star, Integer> starMap = new HashMap<>();
        Map<Asterism, List<Integer>> astMap = new HashMap<>();
        Map<Constellation, List<Integer>> constMap = new HashMap<>();

        for (Star star : starList) {
            starMap.put(star, starList.indexOf(star));
        }

        for (Asterism ast : asterisms) {

            List<Integer> index = new ArrayList<>();

            checkArgument(starList.containsAll(ast.stars()));

            for (Star star : ast.stars()) {
                index.add(starMap.get(star));
            }
            astMap.put(ast, Collections.unmodifiableList(index));
        }
        for (Constellation constellation : constellations) {
            List<Integer> index = new ArrayList<>();
            checkArgument(starList.containsAll(constellation.asterism().stars()));
            for (Star star : constellation.asterism().stars()) {
                index.add(starMap.get(star));
            }
            constMap.put(constellation, Collections.unmodifiableList(index));
        }
        asterismMap = Map.copyOf(astMap);
        constellationMap = Map.copyOf(constMap);
    }

    /**
     * StarCatalogue method stars returning the list of stars
     *
     * @return starList (List<Stars>) : returning the list of stars
     */
    public List<Star> stars() {
        return starList;
    }

    /**
     * StarCatalogue method asterisms returning the set of asterisms
     *
     * @return asterismMap (Set<Asterism>) : returning the key set of the map of asterism
     */
    public Set<Asterism> asterisms() {
        return asterismMap.keySet();
    }

    /**
     * StarCatalogue method stars returning the list of stars
     *
     * @return starList (List<Stars>) : returning the list of stars
     */
    public List<Integer> asterismIndices(Asterism asterism) {
        return asterismMap.get(asterism);
    }

    /**
     * Public method constellations returning the set constellations of the star catalogue
     * 
     * @return (Set<Constellation>) : returning the set of constellations
     */
    public Set<Constellation> constellations() {
        return constellationMap.keySet();
    }

    /**
     * Public method constellationIndices returning the index of the constellation entered in parameters
     * 
     * @param constellation
     * @return
     */
    public List<Integer> constellationIndices(Constellation constellation) {
        return constellationMap.get(constellation);
    }

    // A builder of a catalogue of stars
    public static final class Builder {

        private final List<Star> starBuild;
        private final List<Asterism> asterismBuild;
        private final List<Constellation> constBuild;

        /**
         * StarCatalogue.Builder public constructor initializing the list of stars and asterisms
         */
        public Builder() {

            starBuild = new ArrayList<>();
            asterismBuild = new ArrayList<>();
            constBuild = new ArrayList<>();
        }

        /**
         * StarCatalogue.Builder public method adding a star to the builder
         *
         * @param star (Star) : gives the star we want to add to the builder
         * @return builder (Builder) : return the builder
         */
        public Builder addStar(Star star) {

            starBuild.add(star);

            return this;
        }

        /**
         * StarCatalogue.Builder public method returning an unmodifiable list of the stars in the star catalogue builder
         *
         * @return starBuild (List<Star>) : return the list of stars unmodifiable
         */
        public List<Star> stars() {
            return Collections.unmodifiableList(starBuild);
        }


        /**
         * StarCatalogue.Builder public method adding a star to the builder
         *
         * @param asterism (Asterism) : gives the asterism we want to add to the builder
         * @return builder (Builder) : return the builder
         */
        public Builder addAsterism(Asterism asterism) {

            asterismBuild.add(asterism);

            return this;
        }

        /**
         * StarCatalogue.Builder public method returning an modifiable list of the asterisms in the catalogue builder
         *
         * @return asterismBuild (List<Asterism>) : return the list of asterism unmodifiable
         */
        public List<Asterism> asterisms() {
            return Collections.unmodifiableList(asterismBuild);
        }

        /**
         * Public method addConstellation adding a constellation to the list
         * 
         * @param constellation (Constellation) : gives the constellation to add to the builder
         * @return (Builder) : return the builder with the new constellation added
         */
        public Builder addConstellation(Constellation constellation) {

            constBuild.add(constellation);

            return this;
        }

        /**
         * Public method constellations returning the list of constellations
         * 
         * @return (List<Constellation>) : return the list of constellations 
         */
        public List<Constellation> constellations() {
            return Collections.unmodifiableList(constBuild);
        }

        /**
         * StarCatalogue.Builder public method loading the input into the loader and throws an IOException
         *
         * @param inputStream (inputStream) : gives the input we want to load into the builder
         * @param loader      (Loader) : gives the loader we want to use with the input given
         * @return builder (Builder) : return the builder
         * @throws IOException : throws IOException if fails
         */
        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException {

            loader.load(inputStream, this);

            return this;
        }

        /**
         * StarCatalogue.Builder public method returning the starCatalogue instantiated
         *
         * @return builder (Builder) : return the catalogue of star
         */
        public StarCatalogue build() {
            return new StarCatalogue(starBuild, asterismBuild, constBuild);
        }
    }

    // A loader for the star catalogue
    public interface Loader {

        /**
         * StarCatalogue.Loader abstract method throwing an IOException
         *
         * @param inputStream (inputStream) : gives the input we want to load into the builder
         * @param builder     (builder) : gives the builder we want to load with the input given
         * @throws IOException : throws IOException
         */
        void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
