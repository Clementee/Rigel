package ch.epfl.rigel.math;

import java.util.Locale;

import ch.epfl.rigel.Interval;

/**
 * A Right Open Interval
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class RightOpenInterval extends Interval {
    private double lowerBound, upperBound;
    public static RightOpenInterval rightOpenInterval;

    // pose des problèmes de duplications, cf closed interval
    protected RightOpenInterval(double low, double high) {
        super(low, high);
        lowerBound = low;
        upperBound = high;

    }

    public static RightOpenInterval symmetric(double size) {

        if (size <= 0) {
            throw new IllegalArgumentException();
        } else {
            rightOpenInterval = new RightOpenInterval(-size / 2, size / 2);
            return rightOpenInterval;
        }
    }

    public static RightOpenInterval of(double low, double high) {

        if (low < high) {
            rightOpenInterval = new RightOpenInterval(low, high);
            return rightOpenInterval;
        } else {
            throw new IllegalArgumentException();
        }
    }

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
