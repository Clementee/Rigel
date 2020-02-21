package ch.epfl.rigel.math;
/**
 * An angle
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Angle {
    public final static double TAU = 2 * Math.PI;
    private final static double SEC_PER_DEG = 3600;
    private final static int MIN_TO_SEC = 60;
    private final static double RAD_PER_HOUR = TAU / 24;

    /**
     * Method normalizing the angle
     *
     * @param rad (double) : the angle in radians
     * @return (double) : the angle in radians belonging to the interval [0,2*pi]
     */
    public static double normalizePositive(double rad) {
        if (rad < 0) {
            rad = -rad;
        }

        return rad % TAU;
    }

    /**
     * Method allowing to convert an angle from seconds to radians(and normalize it)
     *
     * @param sec (double) : the angle in seconds
     * @return (double) : the normalized angle in radians
     */
    public static double ofArcsec(double sec) {
        return Math.toRadians(sec / SEC_PER_DEG);
    }

    /**
     * Method converting an angle in degrees, minutes and seconds to an angle in
     * radians (and normalize it). If the measures are not conform, an
     * IllergalArgumentException is thrown
     *
     * @param deg (int) : the number of degrees
     * @param min (int) : the number of minutes in the interval [0,60[
     * @param sec (double) : the number of seconds in the interval [0,60[
     * @return (double) : the normalized angle in radians
     * @throws IllegalArgumentException
     */
    public static double ofDMS(int deg, int min, double sec) {
        if (min < 0 || min >= 60 || sec < 0 || sec >= 60) {
            throw new IllegalArgumentException();
        } else {
            double angle = deg + (min * MIN_TO_SEC + sec) / SEC_PER_DEG;
            return Math.toRadians(angle);
        }
    }

    /**
     * Method converting an angle in degrees to a normalized angle in radians
     *
     * @param deg (double) : the angle in degrees
     * @return (double) : the angle in radians
     */
    public static double ofDeg(double deg) {
        return Math.toRadians(deg);
    }

    /**
     * Method converting an angle in radians to a normalized angle in degrees
     *
     * @param rad (double) : the angle in radians
     * @return (double) : the normalized angle in degrees.
     */
    public static double toDeg(double rad) {
        return Math.toDegrees(rad);
    }

    /**
     * Method converting an angle in hour to a normalized angle in radians
     *
     * @param hr (double) : the angle in hour
     * @return (double) : the angle in radians
     */
    public static double ofHr(double hr) {
        return hr * RAD_PER_HOUR;
    }

    /**
     * Method converting an angle in radians to a normalized angle in hours
     *
     * @param rad (double) : the angle in radians
     * @return (double) : the normalized angle in hours
     */
    public static double toHr(double rad) {
        return rad / RAD_PER_HOUR;
    }
}
