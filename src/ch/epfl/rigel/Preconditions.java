package ch.epfl.rigel;

public final class Preconditions {
    private Preconditions() {
    }
    
        // public method checking if the argument selected is valid or not and if not, throwing an exception
        public static void checkArgument (boolean isTrue) {
            
            if(!isTrue) {
                throw new IllegalArgumentException();
            }
        }
        
     // public method checking if the value chosen is valid in the interval studied. If not throwing exception, else throw the value
     public static double checkInterval (Interval interval, double value) {
          
          double lowerBound;
          double upperBound;
            
            
          lowerBound = interval.low();
          upperBound = interval.high();
            
            if(lowerBound < value  && value< upperBound) {
                return value;
            }
            
            else {
                throw new IllegalArgumentException();
            }
        }
        
}
