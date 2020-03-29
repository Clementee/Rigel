package ch.epfl.rigel.math;

import java.util.Locale;

import static ch.epfl.rigel.Preconditions.checkArgument;

/**
 * A Closed Interval
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public class ClosedInterval extends Interval {

    public static ClosedInterval closedInterval;
    private double lowerBound;
    private double upperBound;

    /**
     * ClosedInterval private constructor
     *
     * @param low  (double) : gives the lower bound of the closed interval
     * @param high (double) : gives the upper bound of the closed interval
     */
    private ClosedInterval(double low, double high) {
        super(low, high);
        lowerBound = low;
        upperBound = high;
    }

    /**
     * ClosedInterval method returning the closed interval between the two bounds if possible and else throw an exception
     *
     * @param low  (double) : gives the lower bound of the closed interval
     * @param high (double) : gives the upper bound of the closed interval
     * @return closedInterval (ClosedInterval) : return the closed interval
     * @throws IllegalArgumentException : if bounds are illegal
     */
    public static ClosedInterval of(double low, double high) {

        checkArgument(low < high);
            closedInterval = new ClosedInterval(low, high);
            return closedInterval;
    }

    /**
     * ClosedInterval method returning the closed interval centered in 0 and of radius size if possible and else throw an exception
     *
     * @param size (double) : gives the value for half the size of the interval
     * @return closedInterval (ClosedInterval) : return the closed interval
     * @throws IllegalArgumentException : if size illegal
     */
    public static ClosedInterval symmetric(double size) {

        checkArgument(size>0);
            closedInterval = new ClosedInterval(-size / 2, size / 2);
            return closedInterval;
    }

    /**
     * Method returning a boolean following the presence or the absence of a value in an interval
     *
     * @param v (double) : gives the chosen value
     */
    @Override
    public boolean contains(double v) {
        return lowerBound <= v && v <= upperBound;
    }

    /**
     * Method that applies the clip-function to a value
     *
     * @param v (double) the value to clipper
     * @return (double) : the image of the clip function
     */
    public double clip(double v) {

        if (v < lowerBound) {
            return lowerBound;
        }
        else return Math.min(v, upperBound);
    }

    /**
     * Method that redefine the toString method
     *
     * @return (String) : the readable version of our interval
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "The closed interval chosen can be represented as [%s,%s]", lowerBound, upperBound);
    }
}
