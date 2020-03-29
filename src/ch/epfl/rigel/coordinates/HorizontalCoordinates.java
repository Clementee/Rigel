package ch.epfl.rigel.coordinates;

import java.util.Locale;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import static ch.epfl.rigel.Preconditions.checkInInterval;

/**
 * An horizontal coordinate
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class HorizontalCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval AZINTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private final static ClosedInterval ALTINTERVAL =  ClosedInterval.symmetric(Angle.TAU/2.0);

    /**
     * HorizontalCoordinates private constructor
     * @param azimuth    (double) : gives the azimuth of the position
     * @param altitude   (double) : gives the altitude of the position
     */
    private HorizontalCoordinates(double azimuth, double altitude) { super(azimuth, altitude);}

    /**
     * Public method used to call the private constructor while throwing an exception if not working
     * @param az    (double) : gives the azimuth value in rad of the position
     * @param alt   (double) : gives the altitude value in rad of the position
     *
     * @return      call the constructor with the entered parameters or throw exception
     */
    public static HorizontalCoordinates of(double az, double alt) {
        checkInInterval(AZINTERVAL,az);
        checkInInterval(ALTINTERVAL,alt);
        return new HorizontalCoordinates(az,alt);
    }

    /**
     * Public method used to call the private constructor while throwing an exception if not working
     * @param azDeg    (double) : gives the azimuth value in degrees of the position
     * @param altDeg   (double) : gives the altitude value in degrees of the position
     *
     * @return      call the constructor with the entered parameters or throw exception
     */
    public static HorizontalCoordinates ofDeg(double azDeg,double altDeg) {
        checkInInterval(AZINTERVAL,Angle.ofDeg(azDeg));
        checkInInterval(ALTINTERVAL,Angle.ofDeg(altDeg));
        return new HorizontalCoordinates(azDeg,altDeg);
    }

    /**
     * Public method used to return the value of the azimuth in radians
     *
     * @return      call the super method for the longitude
     */
    public double az() { return super.lon();}

    /**
     * Public method used to return the value of the azimuth in degrees
     *
     * @return      call the super method for the longitude in degrees
     */
    public double azDeg() { return super.lonDeg();}

    /**
     * Public method used to return the correct string for the direction following the position
     * @param n    (String) : is one of the strings used to describe the direction
     * @param e    (String) : is one of the strings used to describe the direction
     * @param s    (String) : is one of the strings used to describe the direction
     * @param w    (String) : is one of the strings used to describe the direction
     *
     * @return      the cardinal position knowing the position
     */
    public String azOctantName(String n, String e, String s, String w) {

        //dividing the figure in equal octants and knowing the position of the obtained octant, return a string
        double valueOctant = Math.floor((azDeg()+22.5)/45);
        String string = "";

        switch((int)valueOctant) {

            case 0 :
            case 8 :
                string= n;
                break;
            case 1 :
                string = n+e;
                break;
            case 2 :
                string = e;
                break;
            case 3 :
                string =s+e;
                break;
            case 4 :
                string = s;
                break;
            case 5 :
                string = s+w;
                break;
            case 6 :
                string = w;
                break;
            case 7 :
                string = n+w;
                break;
            default :
                break;
        }
        return string;
    }

    /**
     * Public method used to return the value of the altitude in radians
     *
     * @return      call the super method for the latitude
     */
    public double alt() { return super.lat();}

    /**
     * Public method used to return the value of the altitude in degrees
     *
     * @return      call the super method for the latitude in degrees
     */
    public double altDeg() { return super.latDeg();}

    /**
     * Public method used to call the private constructor while throwing an exception if not working
     * @param that             (HorizontalCoordinates) : takes as parameter the horizontal coordinates of the studied point
     *
     * @return angularDistance (double) :    return value of the angular distance between this and that in rad
     */
    public double angularDistanceTo(HorizontalCoordinates that) {
        double currentLongitude = this.lon();
        double currentLatitude = this.lat();
        return Math.acos(Math.sin(currentLatitude)*Math.sin(that.lat())+Math.cos(currentLatitude)*Math.cos(that.lat())*Math.cos(currentLongitude-that.lon()));
    }

    @Override
    public String toString() { return String.format(Locale.ROOT,"(az=%.4f°, alt=%.4f°)",azDeg(),altDeg());}
}
