package ch.epfl.rigel;

public abstract class Interval {
    private final double LOWERBOUND, HIGHERBOUND;

    protected Interval(double lowerBound, double higherBound) {
        LOWERBOUND = lowerBound;
        HIGHERBOUND = higherBound;
    }

    public double low() {
        return LOWERBOUND;
    }

    public double high() {
        return HIGHERBOUND;
    }

    public double size() {
        return HIGHERBOUND - LOWERBOUND;
    }

    public abstract boolean contains(double v);

    @Override
    public final int hashCode() {
        return super.hashCode();
    }
    
    public final boolean equals() {
        
    }
}
