package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static ch.epfl.rigel.math.Angle.*;
//import ch.epfl.rigel.astronomy.Epoch;




public final class SiderealTime {
    private final static int MILLITOSECONDS = 1000;
    private final static int SECONDSTOHOURS = 3600;
    private SiderealTime(){}
    /**
     * Method giving the sidereal Greenwich time
 * @param when (ZonedDateTime) : a date/hour couple with a time zone
     * @return (double) : the Sidereal Greenwich time in radians(in the interval [0, PI[
     */
    public static double greenwich(ZonedDateTime when){
        ZonedDateTime whenBis = when.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(Epoch.J2000.julianCenturiesUntil(Epoch.J2010.thisZoneTime));
        double T =Epoch.J2000.julianCenturiesUntil(whenBis.truncatedTo(ChronoUnit.DAYS));
        System.out.println("T = "+T);
        double t = whenBis.getHour()+whenBis.getMinute()/60.0+whenBis.getSecond()/3600.0;
        System.out.println("t = "+t);
        double S0 = T*T*0.00025862+2400.051335*T+6.697374558;
        System.out.println("S0 = "+S0);
        double S1 = 1.002737909*t;
        System.out.println("S1 = " +S1);
        double Sg = S0+S1;
        System.out.println("Sg = "+Sg);
        return ofHr(Sg);
    }

    public static double local(ZonedDateTime when, GeographicCoordinates where){
        double Sg = greenwich(when);
        double λ = ofDeg(where.lonDeg());
        return Sg + λ; }

}
