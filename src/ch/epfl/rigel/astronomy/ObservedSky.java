package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;

import java.time.ZonedDateTime;
import java.util.*;

import static ch.epfl.rigel.astronomy.Epoch.J2010;
import static ch.epfl.rigel.astronomy.SunModel.*;

public class ObservedSky {

    private static Sun sun;
    private static Moon moon;

    private static CartesianCoordinates sunCoords, moonCords;

    private StarCatalogue catalogue;

    private List<Planet> planetList;
    private List<Star> starList;
    private List<CartesianCoordinates> planetCoords, starCoords;
    private Map<CelestialObject, CartesianCoordinates>  celestialObjectMap;

    /**
     * ObservedSky public constructor initializing some values
     *
     * @param when (ZoneDateTime) : the date / time zone couple
     * @param where (GeographicCoordinates) : the place where we observe the sky
     * @param stereographicProjection (StereographicProjection) : the projection allowing to have all of our Celestial Object in our 2D plan
     * @param catalogue (StarCatalogue) : the data base of all the stars and the asterisms
     */
    public ObservedSky(ZonedDateTime when, GeographicCoordinates where, StereographicProjection stereographicProjection, StarCatalogue catalogue) {

        planetList = new ArrayList<>();
        starList = new ArrayList<>();
        planetCoords = new ArrayList<>();
        starCoords = new ArrayList<>();

        celestialObjectMap = new HashMap<>();

        double daysSinceJ210 = J2010.daysUntil(when);

        this.catalogue = catalogue;

        EquatorialToHorizontalConversion equatorialToHorizontalConversion = new EquatorialToHorizontalConversion(when, where);
        EclipticToEquatorialConversion eclipticToEquatorialConversion = new EclipticToEquatorialConversion(when);

        sun = SUN
                .at(daysSinceJ210, eclipticToEquatorialConversion);
        sunCoords = stereographicProjection
                .apply(equatorialToHorizontalConversion.apply(sun.equatorialPos()));

        celestialObjectMap.put(sun, sunCoords);

        moon = MoonModel.MOON
                .at(daysSinceJ210, eclipticToEquatorialConversion);
        moonCords = stereographicProjection
                .apply(equatorialToHorizontalConversion.apply(moon.equatorialPos()));

        celestialObjectMap.put(moon, moonCords);

        List<PlanetModel> planets = PlanetModel.ALL;

        for (PlanetModel planet : planets) {

            if (!planet.equals(PlanetModel.EARTH)) {

                Planet pl = planet.at(daysSinceJ210, eclipticToEquatorialConversion);
                CartesianCoordinates coord = stereographicProjection
                        .apply(equatorialToHorizontalConversion.apply(pl.equatorialPos()));

                planetList.add(pl);
                planetCoords.add(coord);
                celestialObjectMap.put(pl, coord);
            }
        }

        starList = catalogue.stars();

        for (Star star : starList) {

            CartesianCoordinates starCoord = stereographicProjection
                    .apply(equatorialToHorizontalConversion.apply(star.equatorialPos()));

            starCoords.add(starCoord);
            celestialObjectMap.put(star, starCoord);
        }
    }

    /**
     * ObservedSky public method returning the sun
     *
     * @return sun  (Sun) : return the sun
     */
    public Sun sun() {
        return sun;
    }

    /**
     * ObservedSky public method returning the position of the sun in the observed sky
     *
     * @return sunCoords  (CartesianCoordinates) : return the coordinates of the sun
     */
    public CartesianCoordinates sunPosition(){
        return sunCoords;
    }

    /**
     * ObservedSky public method returning the moon
     *
     * @return moon  (Moon) : return the moon
     */
    public Moon moon(){
        return moon;
    }

    /**
     * ObservedSky public method returning the position of the moon in the observed sky
     *
     * @return moonCoords  (CartesianCoordinates) : return the coordinates of the moon
     */
    public CartesianCoordinates moonPosition(){
        return moonCords;
    }

    /**
     * ObservedSky public method returning the list of planets
     *
     * @return (List<Planet>) : return the list of planets
     */
    public List<Planet> planets(){
        return List.copyOf(planetList);
    }

    /**
     * ObservedSky public method returning the list of stars
     *
     * @return (List<Star>) : return the list of stars
     */
    public List<Star> stars(){
        return List.copyOf(starList);
    }

    /**
     * Observed sky public method giving all the stars positions
     *
     * @return (double[]) :  an array containing all the cartesian coordinates, for example,
     *                       if our array contains only the coordinates(x,y) of the Celestial Object,
     *                       the array will be [x ; y]
     */
    public double[] starsPosition(){

        double[] tab = new double[10134];
        int i =0;

        for (CartesianCoordinates coordinates : starCoords) {

            tab[i]=coordinates.x();
            i++;
            
            tab[i]=coordinates.y();
            i++;
        }

        return tab;
    }

    /**
     * ObservedSky public method giving all the planet positions
     *
     * @return (double[]) :  an array containing all the cartesian coordinates, for example,
     *                       if our array contains only the coordinates(x,y) of the Celestial Object,
     *                       the array will be [x ; y]
     */
    public double[] planetsPosition(){

        double[] tab = new double[14];
        int i =0;

        for (CartesianCoordinates planetCoord : planetCoords) {

            tab[i]=planetCoord.x();
            i++;
            
            tab[i]=planetCoord.y();
            i++;
        }

        return tab;
    }

    /**
     * ObservedSky public method returning the set of asterisms in the observed sky
     *
     * @return (Set<Asterism>) : return the set of asterisms
     */
    public Set<Asterism> asterism(){
        return Set.copyOf(catalogue.asterisms());
    }

    public List<Integer> asterismIndexList(Asterism asterism){
        return catalogue.asterismIndices(asterism); 
    }

    /**
     * Method finding the closest Celestial object to a point, here the cursor, or returning nothing if there is no object closest than the minimal distance
     *
     * @param coordinates (CartesianCoordinates) : the position of our cursor
     * @param distance (double) : the minimal distance
     * @return (Optional (CelestialObject)) : an Optional containing our closest Celestial object or nothing if the object does not exist
     */
    public Optional<CelestialObject> objectClosestTo(CartesianCoordinates coordinates, double distance) {

        MapComparator comp = new MapComparator(celestialObjectMap, coordinates);
        CelestialObject selectedObject = Collections.min(celestialObjectMap.keySet(), comp);

        if (celestialObjectMap.containsKey(selectedObject) && (distance(celestialObjectMap.get(selectedObject), coordinates)) <= distance){

            return Optional.of(selectedObject);
        }

        else{

            return Optional.empty();
        }
    }

    /**
     * ObservedSky private method returning the distance between two coordinates entered in parameters
     *
     * @param o1 (CartesianCoordinates) : gives the cartesian coordinates of the first object we want to compare
     * @param o2 (CartesianCoordinates) : gives the cartesian coordinates of the second object
     * @return (double) : the distance between the two objects
     */
    private static double distance(CartesianCoordinates o1, CartesianCoordinates o2){

        return Math.hypot( o1.x() - o2.x() , o1.y() - o2.y());
    }

    //Private class MapComparator which role is to compare two maps
    private static class MapComparator implements Comparator {

        private CartesianCoordinates pointer;

        private Map<CelestialObject, CartesianCoordinates> map;

        /**
         * MapComparator private constructor initializing some values
         *
         * @param map      (Map<CelestialObject, CartesianCoordinates>) : gives the map of celestial objects linked with their coordinates
         * @param pointer  (CartesianCoordinates) : gives the cartesian coordinates of the position from which we observe the sky
         */
        private MapComparator(Map<CelestialObject, CartesianCoordinates> map, CartesianCoordinates pointer){

            this.map=map;
            this.pointer=pointer;
        }

        /**
         * MapComparator public method comparing the distance between two objects and returning a positive value if the distance with the first object is bigger than the other one
         *
         * @param o1 (Object) : gives the first object we want to compare
         * @param o2 (Object) : gives the second object we want to compare
         * @return (int) : either a positive or a negative value depending on which one of the two objects is closer from the observer
         */
        @Override
        public int compare(Object o1, Object o2) {

            if ( distance(map.get(o1), pointer) >= distance(map.get(o2), pointer)) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }
}
