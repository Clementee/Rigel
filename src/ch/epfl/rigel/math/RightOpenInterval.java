package ch.epfl.rigel.math;

import java.util.Locale;

/**
 * A Right Open Interval
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class RightOpenInterval extends Interval {
    private double lowerBound, upperBound;
    public static RightOpenInterval rightOpenInterval;

    /**
     * RightOpenInterval private constructor
     *
     * @param low  (double) : gives the lower bound of the right open interval
     * @param high (double) : gives the upper bound of the right open interval
     */
    protected RightOpenInterval(double low, double high) {
        super(low, high);
        lowerBound = low;
        upperBound = high;

    }

    /**
     * RightOpenInterval method returning the right open interval between the two bounds if possible and else throw an exception
     *
     * @param low  (double) : gives the lower bound of the right open interval
     * @param high (double) : gives the upper bound of the right open interval
     * @return RightOpenInterval (RightOpenInterval) : return the right open interval
     * @throws IllegalArgumentException
     */
    public static RightOpenInterval symmetric(double size) {

        if (size <= 0) {
            throw new IllegalArgumentException();
        } else {
            rightOpenInterval = new RightOpenInterval(-size / 2, size / 2);
            return rightOpenInterval;
        }
    }

    /**
     * RightOpenInterval method returning the right open interval centered in 0 and of radius size if possible and else throw an exception
     *
     * @param size (double) : gives the value for half the size of the interval
     * @return RightOpenInterval (RightOpenInterval) : return the right open interval
     * @throws IllegalArgumentException
     */
    public static RightOpenInterval of(double low, double high) {

        if (low < high) {
            rightOpenInterval = new RightOpenInterval(low, high);
            return rightOpenInterval;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Applies the "reduce" mathematical function, to a variable.
     *
     * @param v (double) : the variable chosen.
     * @return the boolean value of the method, depending on the presence or the absence 
     */
    @Override
    public boolean contains(double v) {
        if (lowerBound <= v && v < upperBound) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Applies the "reduce" mathematical function, to a variable.
     *
     * @param v (double) : the variable chosen.
     * @return the numerical application of this function
     */
    public double reduce(double v) {
        double x = v - lowerBound;
        double y = upperBound - lowerBound;
        double floorMod = x - (y * Math.floor(x / y));
        return lowerBound + floorMod;
    }

    /**
     * Method that redefine the toString method
     *
     * @return (String) : a readable version of the Interval
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "The right open interval chosen can be represented as [%s,%s[",
                lowerBound, upperBound);

    }
}
