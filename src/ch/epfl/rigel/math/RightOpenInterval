package ch.epfl.rigel.math;

import java.util.Locale;

import ch.epfl.rigel.Interval;

public final class RightOpenInterval extends Interval {
    private double lowerBound, upperBound;
    public RightOpenInterval rightOpenInterval;

    // pose des problèmes de duplications, cf closed interval
    protected RightOpenInterval(double lowerBound, double higherBound) {
        super(lowerBound, higherBound);
    }

    public RightOpenInterval symmetric(double size) {

        if (size == 0) {
            throw new IllegalArgumentException();
        } else {
            rightOpenInterval = new RightOpenInterval(-size, size);
            return rightOpenInterval;
        }
    }

    public RightOpenInterval of(double low, double high) {

        if (low <= high) {
            rightOpenInterval = new RightOpenInterval(low, high);
            return rightOpenInterval;
        }

        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean contains(double v) {
        return (v >= lowerBound && v < upperBound);
    }

    /**
     * Applies the "reduce" mathematical function, to a variable.
     * 
     * @param v
     *            (double) : the variable chosen.
     * @return the numerical application of this function
     */
    public double reduce(double v) {
        double x = v - lowerBound;
        double y = upperBound - v;
        return lowerBound + (x - y * x / y);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "The right open interval chosen can be represented as [%s,%s[",
                lowerBound, upperBound);

    }
}
