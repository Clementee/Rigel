package ch.epfl.rigel.math;

/**
 * An Interval
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public abstract class Interval {

    private final double lowerBound, upperBound;

    /**
     * Protected constructor for Interval
     * 
     * @param lowerBound (double) : gives the lower bound of the interval
     * @param upperBound (double) : gives the upper bound of the interval
     */
    protected Interval(double lowerBound, double upperBound) {

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Method giving the lower bound
     *
     * @return (double) : the lower bound of the interval
     */
    public double low() {
        return lowerBound;
    }

    /**
     * Method giving the upper bound
     *
     * @return (double) : the upper bound of the interval
     */
    public double high() {
        return upperBound;
    }

    /**
     * Method giving the size of the interval
     *
     * @return (double) :  the size of the interval
     */
    public double size() {
        return upperBound - lowerBound;
    }

    /**
     * (To be redefined) : Method allowing to know if a value v is contained in the interval
     *
     * @param v (double) : the value
     * @return (boolean) : if the value is contained (true) or not (false)
     */
    public abstract boolean contains(double v);

    /**
     * Method throwing an UnsupportedOperationException (UOE)
     *
     * @return (int) : nothing because an error has already been thrown
     * @throws UnsupportedOperationException :
     */
    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method throwing an UOE
     *
     * @param obj (Object) : any object
     * @return (boolean) : nothing because an error has already been thrown
     * @throws UnsupportedOperationException :
     */
    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}
