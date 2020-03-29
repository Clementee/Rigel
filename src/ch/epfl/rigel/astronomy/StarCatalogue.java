package ch.epfl.rigel.astronomy;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public final class StarCatalogue {

    private List<Star> starList;
    private Map<Asterism,List<Integer>> asterismMap = new HashMap<>();


    public StarCatalogue(List<Star> stars, List<Asterism> asterisms){
        starList = stars;
        for(Asterism ast : asterisms){
            List<Integer> index = new LinkedList<>();

            if(!stars.containsAll(ast.stars())){
                throw new IllegalArgumentException();
            }
            else {
                for(Star star : ast.stars()){
                    index.add(starList.indexOf(star));
                }
                asterismMap.put(ast,index);
            }
        }

    }

    public List<Star> stars(){
        return starList;
    }

    public Set<Asterism> asterisms(){
        return asterismMap.keySet();
    }

    public List<Integer> asterismIndices(Asterism asterism){
        return List.copyOf(asterismMap.get(asterism));
    }


    public static final class Builder {

        private List<Star> starBuild;
        private List<Asterism> asterismBuild;

        public Builder(){
            starBuild = new ArrayList<>();
            asterismBuild = new ArrayList<>();
        }

        public Builder addStar(Star star){
            starBuild.add(star);
            return this;
        }

        public List<Star> stars(){
            return Collections.unmodifiableList(starBuild);
        }

        public Builder addAsterism(Asterism asterism){
            asterismBuild.add(asterism);
            return this;
        }

        public List<Asterism> asterisms(){
            return Collections.unmodifiableList(asterismBuild);
        }

        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException {
            loader.load(inputStream, this);
            return this;
        }

        public StarCatalogue build(){
            return new  StarCatalogue(starBuild, asterismBuild);
        }
    }

    public interface Loader {
        void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
