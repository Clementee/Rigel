package ch.epfl.rigel.astronomy;

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

    private List<Star> starList;
    private Map<Asterism,List<Integer>> asterismMap = new HashMap<>();


    /**
     * StarCatalogue public constructor creating and initializing a catalogue of stars
     *
     * @param stars     (List<Star>) : gives the list of stars
     * @param asterisms (List<Asterism>) : gives the list of asterisms
     */
    public StarCatalogue(List<Star> stars, List<Asterism> asterisms){

        starList = List.copyOf(stars);
        for(Asterism ast : asterisms){
            List<Integer> index = new LinkedList<>();

            checkArgument(stars.containsAll(ast.stars()));
                for(Star star : ast.stars()){
                    index.add(starList.indexOf(star));
                }

                asterismMap.put(ast,index);
        }
    }

    /**
     * StarCatalogue method stars returning the list of stars
     * @return starList (List<Stars>) : returning the list of stars
     */
    public List<Star> stars(){
        return List.copyOf(starList);
    }

    /**
     * StarCatalogue method asterisms returning the set of asterisms
     * @return asterismMap (Set<Asterism>) : returning the key set of the map of asterism
     */
    public Set<Asterism> asterisms(){
        return Set.copyOf(asterismMap.keySet());
    }


    /**
     * StarCatalogue method stars returning the list of stars
     * @return starList (List<Stars>) : returning the list of stars
     */
    public List<Integer> asterismIndices(Asterism asterism) {
        checkArgument(asterismMap.containsKey(asterism));
        return List.copyOf(asterismMap.get(asterism));
    }

    // A builder of a catalogue of stars
    public static final class Builder {

        private List<Star> starBuild;
        private List<Asterism> asterismBuild;

        /**
         * StarCatalogue.Builder public constructor initializing the list of stars and asterisms
         */
        public Builder(){
            starBuild = new ArrayList<>();
            asterismBuild = new ArrayList<>();
        }

        /**
         * StarCatalogue.Builder public method adding a star to the builder
         * @param star (Star) : gives the star we want to add to the builder
         * @return builder (Builder) : return the builder
         */
        public Builder addStar(Star star){
            starBuild.add(star);
            return this;
        }

        /**
         * StarCatalogue.Builder public method returning an unmodifiable list of the stars in the star catalogue builder
         * @return starBuild (List<Star>) : return the list of stars unmodifiable
         */
        public List<Star> stars(){
            return Collections.unmodifiableList(starBuild);
        }


        /**
         * StarCatalogue.Builder public method adding a star to the builder
         * @param asterism (Asterism) : gives the asterism we want to add to the builder
         * @return builder (Builder) : return the builder
         */
        public Builder addAsterism(Asterism asterism){
            asterismBuild.add(asterism);
            return this;
        }

        /**
         * StarCatalogue.Builder public method returning an modifiable list of the asterisms in the catalogue builder
         * @return asterismBuild (List<Asterism>) : return the list of asterism unmodifiable
         */
        public List<Asterism> asterisms(){
            return Collections.unmodifiableList(asterismBuild);
        }

        /**
         * StarCatalogue.Builder public method loading the input into the loader and throws an IOException
         * @param inputStream (inputStream) : gives the input we want to load into the builder
         * @param loader      (Loader) : gives the loader we want to use with the input given
         * @return builder (Builder) : return the builder
         */
        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException {
            loader.load(inputStream, this);
            return this;
        }

        /**
         * StarCatalogue.Builder public method returning the starCatalogue instantiated
         * @return builder (Builder) : return the catalogue of star
         */
        public StarCatalogue build(){
            return new  StarCatalogue(starBuild, asterismBuild);
        }
    }

    // A loader for the star catalogue
    public interface Loader {

        /**
         * StarCatalogue.Loader abstract method throwing an IOException
         * @param inputStream (inputStream) : gives the input we want to load into the builder
         * @param builder      (builder) : gives the builder we want to load with the input given
         */
        void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
