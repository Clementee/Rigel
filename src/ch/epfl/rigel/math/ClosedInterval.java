package ch.epfl.rigel.math;

import java.util.Locale;

import ch.epfl.rigel.Interval;

public class ClosedInterval extends Interval {

    public static ClosedInterval closedInterval;
    private double lowerBound;
    private double upperBound;
    
    /**
     * ClosedInterval private constructor
     * @param low   (double) : gives the lower bound of the closed interval
     * @param high  (double) : gives the upper bound of the closed interval
     */
    private ClosedInterval(double low, double high) {
        super(low,high);
        lowerBound = low;
        upperBound = high;     
    }
    
    /**
     * ClosedInterval method returning the closed interval between the two bounds if possible and else throw an exception
     * @param low   (double) : gives the lower bound of the closed interval
     * @param high  (double) : gives the upper bound of the closed interval
     * 
     * @return closedInterval (ClosedInterval) : return the closed interval 
     */
    public static ClosedInterval of(double low, double high) {
        
        if(low<high) {
           closedInterval = new ClosedInterval(low,high);
           return closedInterval;
        }
         
        else {
            throw new IllegalArgumentException();
        }   
    }
    
    /**
     * ClosedInterval method returning the closed interval centered in 0 and of radius size if possible and else throw an exception
     * @param size   (double) : gives the value for half the size of the interval
     * 
     * @return closedInterval (ClosedInterval) : return the closed interval 
     */
    public static ClosedInterval symmetric(double size) {
        
        if(size<=0) {
            throw new IllegalArgumentException();
        }
        else {
        closedInterval = new ClosedInterval(-size/2,size/2);
        return closedInterval;
    }
    }
    
    /**
     * Method returning a boolean following the presence or the absence of a value in an interval
     * @param v   (double) : gives the chosen value
     */
    @Override
    public boolean contains(double v) {
        if(lowerBound<=v && v<=upperBound) {
            return true;
        }
        else {
            return false;
        }
    }
    
    double clip(double v) {
        
        if(v<lowerBound) {
            return lowerBound;
        }
        else if(v>upperBound) {
            return upperBound;
        }
        else {
            return v;
        }
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT,"The closed interval chosen can be represented as [%s,%s]",lowerBound,upperBound);
    }
    
    
    
}
