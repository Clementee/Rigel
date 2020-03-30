package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ch.epfl.rigel.astronomy.Epoch.J2010;

public class ObservedSky {
    private Sun sun;
    private Moon moon;
    private List<Star> starCatalogue;
    public ObservedSky(ZonedDateTime when, GeographicCoordinates where, StereographicProjection stereographicProjection, StarCatalogue catalogue){
        double daysSinceJ210 = J2010.daysUntil(when);
        EclipticToEquatorialConversion eclipticToEquatorialConversion = new EclipticToEquatorialConversion(when);
        sun = SunModel.SUN.at(daysSinceJ210, eclipticToEquatorialConversion);
        moon = MoonModel.MOON.at(daysSinceJ210, eclipticToEquatorialConversion);
        starCatalogue = catalogue.stars();
    }
}
