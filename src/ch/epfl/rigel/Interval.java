package ch.epfl.rigel;
/**
 * An Interval
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public abstract class Interval {
    private final double LOWERBOUND, UPPERBOUND;

    protected Interval(double lowerBound, double upperBound) {
        LOWERBOUND = lowerBound;
        UPPERBOUND = upperBound;
    }

    /**
     * Method giving the lower bound
     *
     * @return (double) : the lower bound of the interval
     */
    public double low() {
        return LOWERBOUND;
    }

    /**
     * Method giving the upper bound
     *
     * @return (double) : the upper bound of the interval
     */
    public double high() {
        return UPPERBOUND;
    }

    /**
     * Method giving the size of the interval
     *
     * @return (double) :  the size of the interval
     */
    public double size() {
        return UPPERBOUND - LOWERBOUND;
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
     * @throw UnsupportedOperationException
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
     * @throws UnsupportedOperationException
     */
    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}
