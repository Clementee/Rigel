package ch.epfl.rigel.coordinates;

import java.util.Locale;

/**
 * CartesianCoordinates
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class CartesianCoordinates {

    private double x;
    private double y;

    /**
     * CartesianCoordinates private constructor initializing some values
     * @param abs     (double) : gives the abscissa of the coordinates
     * @param ord     (double) : gives the ordonnée of the coordinates
     */
    private CartesianCoordinates(double abs, double ord){
        x = abs;
        y = ord;
    }

    /**
     * CartesianCoordinates method returning the cartesian coordinates
     * @param x     (double) : gives the abscissa of the coordinates
     * @param y     (double) : gives the ordonnée of the coordinates
     *
     * @return CartesianCoordinates (CartesianCoordinates) : return the cartesian coordinates linked to the x and y given
     */
    public static CartesianCoordinates of(double x, double y){
        return new CartesianCoordinates(x,y);
    }

    public double x(){
        return x;
    }

    public double y(){
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
