package ch.epfl.rigel;

import ch.epfl.rigel.math.Interval;

/**
 * A Precondition-class with useful methods
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Preconditions {

    /**
     * Preconditions private constructor
     */
    private Preconditions() {}

    /**
     * Method checking if the argument selected is valid or not and if not, throwing an exception
     *
     * @param isTrue (Boolean) : gives the argument of the checked value
     * @throws IllegalArgumentException : if the argument is false, throws IAE
     */
    public static void checkArgument(boolean isTrue) {

        if (!isTrue) {
            throw new IllegalArgumentException("Argument invalid");
        }
    }

    /**
     * Method checking if the value chosen is in the interval studied and throw exception if not
     *
     * @param interval (Interval) : gives the studied interval for which we check if the value is in
     * @param value    (double) : gives the value of the studied number
     * @throws IllegalArgumentException : if the value doesn't belong to the interval, throws IAE
     */
    public static double checkInInterval(Interval interval, double value) {

        if (interval.contains(value)) {
            return value;
        }
        else {
            throw new IllegalArgumentException("Value not in the interval");
        }
    }
}
