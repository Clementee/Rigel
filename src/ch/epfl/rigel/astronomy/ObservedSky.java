package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;

import java.time.Month;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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

    private Map<CartesianCoordinates, CelestialObject>  celestialObjectMap;

    public ObservedSky(ZonedDateTime when, GeographicCoordinates where, StereographicProjection stereographicProjection, StarCatalogue catalogue) {
        double daysSinceJ210 = J2010.daysUntil(when);
        celestialObjectMap = new HashMap<>();
        this.catalogue = catalogue;

        EquatorialToHorizontalConversion equatorialToHorizontalConversion = new EquatorialToHorizontalConversion(when, where);
        EclipticToEquatorialConversion eclipticToEquatorialConversion = new EclipticToEquatorialConversion(when);

        sun = SUN.at(daysSinceJ210, eclipticToEquatorialConversion);
        sunCoords = stereographicProjection.apply(equatorialToHorizontalConversion.apply(sun.equatorialPos()));
        celestialObjectMap.put(sunCoords, sun);

        moon = MoonModel.MOON.at(daysSinceJ210, eclipticToEquatorialConversion);
        moonCords = stereographicProjection.apply(equatorialToHorizontalConversion.apply(moon.equatorialPos()));
        celestialObjectMap.put(moonCords, moon);

        List<PlanetModel> planets = PlanetModel.ALL;
        for (PlanetModel planet : planets) {
            if (!planet.equals(PlanetModel.EARTH)) {
                Planet pl = planet.at(daysSinceJ210, eclipticToEquatorialConversion);
                planetList.add(pl);
                CartesianCoordinates coord = stereographicProjection.apply(equatorialToHorizontalConversion.apply(pl.equatorialPos()));
                planetCoords.add(coord);
                celestialObjectMap.put(coord, pl);
            }
        }

        starList = catalogue.stars();
        for (Star star : starList) {
            CartesianCoordinates starCoord = stereographicProjection.apply(equatorialToHorizontalConversion.apply(star.equatorialPos()));
            starCoords.add(starCoord);
            celestialObjectMap.put(starCoord, star);
        }
    }


    public Sun sun() {return sun;}

    public CartesianCoordinates sunPosition(){return sunCoords;}

    public Moon moon(){return moon;}

    public CartesianCoordinates moonPosition(){return moonCords;}

    public List<Planet> planets(){return planetList;}

    public ArrayList<Double> planetPosition(){
        ArrayList<Double> tab = new ArrayList<>();
        for (CartesianCoordinates planetCoord : planetCoords) {
            tab.add(planetCoord.x());
            tab.add(planetCoord.y());
        }
        return tab;
    }

    public Set<Asterism> asterism(){return catalogue.asterisms();}

    public List<Integer> asterismIndexList(Asterism asterism){return catalogue.asterismIndices(asterism);}

    public Optional<CelestialObject> objectClosestTo(CartesianCoordinates coordinates, double distance){
        double minDistance = distance;
        CartesianCoordinates chosenCoords = coordinates;
        for (CartesianCoordinates cartesianCoordinates : celestialObjectMap.keySet()) {
            if(distance(coordinates, cartesianCoordinates)<=minDistance){
                minDistance = distance(coordinates, cartesianCoordinates);
                chosenCoords = cartesianCoordinates;
            }
        }if(celestialObjectMap.containsKey(chosenCoords)){
            return Optional.of(celestialObjectMap.get(chosenCoords));
        }else{
            return Optional.empty();
        }
    }
    private double distance(CartesianCoordinates coord1, CartesianCoordinates coord2){
        return Math.hypot(coord1.x()-coord2.x(), coord1.y()-coord2.y());
    }
}