package ch.epfl.rigel.coordinates;

import java.util.Locale;

/**
 * CartesianCoordinates
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class CartesianCoordinates {

    private static double x;
    private static double y;

    private CartesianCoordinates(double abs, double ord){
        x = abs;
        y = ord;
    }

    public static CartesianCoordinates of(double x, double y){
        return new CartesianCoordinates(x,y);
    }

    public static double x(){
        return x;
    }

    public static double y(){
        return y;
    }

    @Override
    public final int hashCode(){
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
     
    @Override
    public final String toString(){
        return String.format(Locale.ROOT,"(abscisse =%.4f°, ordonnée =%.4f°)",x(),y());
    }
}
