package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;

import javax.swing.text.html.Option;
import java.time.ZonedDateTime;
import java.util.*;

import static ch.epfl.rigel.astronomy.Epoch.J2010;
import static ch.epfl.rigel.astronomy.SunModel.*;

public class ObservedSky {

    private StarCatalogue catalogue;

    private Sun sun;
    private Moon moon;

    List<Planet> planetList;
    List<Star> starList;

    private CartesianCoordinates sunCoords, moonCords;
    private List<CartesianCoordinates> planetCoords, starCoords;

    private Map<CelestialObject, CartesianCoordinates>  celestialObjectMap;

    public ObservedSky(ZonedDateTime when, GeographicCoordinates where, StereographicProjection stereographicProjection, StarCatalogue catalogue) {
        planetList = new ArrayList<>();
        starList = new ArrayList<>();
        planetCoords = new ArrayList<>();
        starCoords = new ArrayList<>();


        double daysSinceJ210 = J2010.daysUntil(when);
        celestialObjectMap = new HashMap<>();
        this.catalogue = catalogue;

        EquatorialToHorizontalConversion equatorialToHorizontalConversion = new EquatorialToHorizontalConversion(when, where);
        EclipticToEquatorialConversion eclipticToEquatorialConversion = new EclipticToEquatorialConversion(when);

        sun = SUN.at(daysSinceJ210, eclipticToEquatorialConversion);
        sunCoords = stereographicProjection.apply(equatorialToHorizontalConversion.apply(sun.equatorialPos()));
        celestialObjectMap.put(sun, sunCoords);

        moon = MoonModel.MOON.at(daysSinceJ210, eclipticToEquatorialConversion);
        moonCords = stereographicProjection.apply(equatorialToHorizontalConversion.apply(moon.equatorialPos()));
        celestialObjectMap.put(moon, moonCords);

        List<PlanetModel> planets = PlanetModel.ALL;
        for (PlanetModel planet : planets) {
            if (!planet.equals(PlanetModel.EARTH)) {
                Planet pl = planet.at(daysSinceJ210, eclipticToEquatorialConversion);
                planetList.add(pl);
                CartesianCoordinates coord = stereographicProjection.apply(equatorialToHorizontalConversion.apply(pl.equatorialPos()));
                planetCoords.add(coord);
                celestialObjectMap.put(pl, coord);
            }
        }

        starList = catalogue.stars();
        for (Star star : starList) {
            CartesianCoordinates starCoord = stereographicProjection.apply(equatorialToHorizontalConversion.apply(star.equatorialPos()));
            starCoords.add(starCoord);
            celestialObjectMap.put(star, starCoord);
        }

        System.out.println(System.nanoTime()/10e6);
    }


    public Sun sun() {return sun;}

    public CartesianCoordinates sunPosition(){return sunCoords;}

    public Moon moon(){return moon;}

    public CartesianCoordinates moonPosition(){return moonCords;}

    public List<Planet> planets(){return planetList;}

    public List<Star> stars(){return starList;};

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

    public Set<Asterism> asterism(){return catalogue.asterisms();}

    public List<Integer> asterismIndexList(Asterism asterism){return catalogue.asterismIndices(asterism);}

    public Optional<CelestialObject> objectClosestTo(CartesianCoordinates coordinates, double distance) {
        MapCompatarator comp = new MapCompatarator(celestialObjectMap, coordinates);
        CelestialObject selectedObject = Collections.min(celestialObjectMap.keySet(), comp);
        if(celestialObjectMap.containsKey(selectedObject)&&(distance(celestialObjectMap.get(selectedObject), coordinates))<=distance){
            return Optional.of(selectedObject);
        }else{
            return Optional.empty();
        }

    }

    private static class MapCompatarator implements Comparator{
        private CartesianCoordinates pointer;
        private Map<CelestialObject, CartesianCoordinates> map;

        private MapCompatarator(Map<CelestialObject, CartesianCoordinates> map, CartesianCoordinates pointer){
            this.map=map;
            this.pointer=pointer;
        }

        @Override
        public int compare(Object o1, Object o2) {
            if(distance(map.get(o1), pointer)>= distance(map.get(o2), pointer )){
                return 1;
            }
            return -1;
        }
    }

    private static double distance(CartesianCoordinates o1, CartesianCoordinates o2){
        return Math.hypot(o1.x()-o2.x(), o1.y()- o2.y());
    }
}