package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.bonus.Constellation;
import ch.epfl.rigel.coordinates.*;

import java.time.ZonedDateTime;
import java.util.*;

import static ch.epfl.rigel.astronomy.Epoch.J2010;
import static ch.epfl.rigel.astronomy.SunModel.*;

/**
 * The observed sky
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public class ObservedSky {

    private static Sun sun;
    private static Moon moon;

    private static CartesianCoordinates sunCoords, moonCords;

    private final StarCatalogue catalogue;

    private final List<Planet> planetList;
    private List<Star> starList;
    private final List<CartesianCoordinates> planetCoords, starCoords;
    private final Map<CelestialObject, CartesianCoordinates> celestialObjectMap;

    /**
     * ObservedSky public constructor initializing some values
     *
     * @param when                    (ZoneDateTime) : the date / time zone couple
     * @param where                   (GeographicCoordinates) : the place where we observe the sky
     * @param stereographicProjection (StereographicProjection) : the projection allowing to have all of our Celestial Object in our 2D plan
     * @param catalogue               (StarCatalogue) : the data base of all the stars and the asterisms
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

        //iterating over the list of planets to initialize the lists
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

        //iterating over the list of stars to intialize the lists
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
    public CartesianCoordinates sunPosition() {
        return sunCoords;
    }

    /**
     * ObservedSky public method returning the moon
     *
     * @return moon  (Moon) : return the moon
     */
    public Moon moon() {
        return moon;
    }

    /**
     * ObservedSky public method returning the position of the moon in the observed sky
     *
     * @return moonCoords  (CartesianCoordinates) : return the coordinates of the moon
     */
    public CartesianCoordinates moonPosition() {
        return moonCords;
    }

    /**
     * ObservedSky public method returning the list of planets
     *
     * @return (List < Planet >) : return the list of planets
     */
    public List<Planet> planets() {
        return List.copyOf(planetList);
    }

    /**
     * ObservedSky public method returning the list of stars
     *
     * @return (List < Star >) : return the list of stars
     */
    public List<Star> stars() {
        return List.copyOf(starList);
    }

    /**
     * Observed sky public method giving all the stars positions
     *
     * @return (double[]) :  an array containing all the cartesian coordinates, for example,
     * if our array contains only the coordinates(x,y) of the Celestial Object,
     * the array will be [x ; y]
     */
    public double[] starsPosition() {

        double[] tab = new double[10134];
        int i = 0;

        for (CartesianCoordinates coordinates : starCoords) {

            tab[i] = coordinates.x();
            i++;

            tab[i] = coordinates.y();
            i++;
        }

        return tab;
    }

    /**
     * ObservedSky public method giving all the planet positions
     *
     * @return (double[]) :  an array containing all the cartesian coordinates, for example,
     * if our array contains only the coordinates(x,y) of the Celestial Object,
     * the array will be [x ; y]
     */
    public double[] planetsPosition() {

        double[] tab = new double[14];
        int i = 0;

        for (CartesianCoordinates planetCoord : planetCoords) {

            tab[i] = planetCoord.x();
            i++;

            tab[i] = planetCoord.y();
            i++;
        }

        return tab;
    }

    /**
     * ObservedSky public method returning the set of asterisms in the observed sky
     *
     * @return (Set < Asterism >) : return the set of asterisms
     */
    public Set<Asterism> asterism() {
        return Set.copyOf(catalogue.asterisms());
    }

    /**
     * ObservedSky public method returning the indexes of an asterism entered in parameters
     *
     * @param asterism (Asterism) : gives the asterism from which we want ot get the parameters
     * @return (List<Integer>) : return the list of indexes for the selected asterism
     */
    public List<Integer> asterismIndexList(Asterism asterism) {
        return catalogue.asterismIndices(asterism);
    }

    /**
     * ObservedSky public method returning the set of constellations in the observed sky
     *
     * @return (Set <Constellation>) : return the set of constellations
     */
    public Set<Constellation> constellations(){return Set.copyOf(catalogue.constellations());}

    /**
     * Method finding the closest Celestial object to a point, here the cursor, or returning nothing if there is no object closest than the minimal distance
     *
     * @param coordinates (CartesianCoordinates) : the position of our cursor
     * @param distance    (double) : the minimal distance
     * @return (Optional ( CelestialObject)) : an Optional containing our closest Celestial object or nothing if the object does not exist
     */
    public Optional<CelestialObject> objectClosestTo(CartesianCoordinates coordinates, double distance) {

        CelestialObject selectedObject = Collections.min(celestialObjectMap.keySet(), new Comparator<CelestialObject>() {
            @Override
            public int compare(CelestialObject o1, CelestialObject o2) {
                return (distance(celestialObjectMap.get(o1), coordinates) < distance(celestialObjectMap.get(o2), coordinates)) ? -1 : (distance(celestialObjectMap.get(o1), coordinates) == distance(celestialObjectMap.get(o2), coordinates)) ? 1 : 0;
            }
        });


        if (celestialObjectMap.containsKey(selectedObject) && (distance(celestialObjectMap.get(selectedObject), coordinates)) <= distance) {

            return Optional.of(selectedObject);
        } else {

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
    private static double distance(CartesianCoordinates o1, CartesianCoordinates o2) {

        return Math.hypot(o1.x() - o2.x(), o1.y() - o2.y());
    }
}
