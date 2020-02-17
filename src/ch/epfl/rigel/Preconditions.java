package ch.epfl.rigel;

public final class Preconditions {
    private Preconditions() {
    }
        public static void checkArgument (boolean isTrue) {
            if(!isTrue) {
                throw new IllegalArgumentException();
            }
        }
        
     public static double checkInterval (Interval interval, double value) {
          
          double lowerBound;
          double upperBound;
            
            
          lowerBound = interval.low();
          upperBound = interval.high();
            
            if(lowerBound <= value <= upperBound) {
                return value;
            }
            
            else {
                throw new IllegalArgumentException();
            }
        }
        
}
