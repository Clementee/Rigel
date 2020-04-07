package ch.epfl.rigel.coordinates;

import java.util.Locale;

/**
 * CartesianCoordinates
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class CartesianCoordinates {

    private final double x;
    private final double y;

    /**
     * CartesianCoordinates private constructor initializing some values
     *
     * @param abs (double) : gives the abscissa of the coordinates
     * @param ord (double) : gives the ordonnée of the coordinates
     */
    private CartesianCoordinates(double abs, double ord) {

        x = abs;
        y = ord;
    }

    /**
     * CartesianCoordinates method returning the cartesian coordinates
     *
     * @param x (double) : gives the abscissa of the coordinates
     * @param y (double) : gives the ordonnée of the coordinates
     * @return CartesianCoordinates (CartesianCoordinates) : return the cartesian coordinates linked to the x and y given
     */
    public static CartesianCoordinates of(double x, double y) {

        return new CartesianCoordinates(x, y);
    }

    /**
     * CartesianCoordinates method returning the x of the coordinates
     *
     * @return x  (double) : return the abscissa of the coordinates
     */
    public double x() {

        return x;
    }

    /**
     * CartesianCoordinates method returning the y of the coordinates
     *
     * @return y  (double) : return the y of the coordinates
     */
    public double y() {

        return y;
    }

    /**
     * CartesianCoordinates overrode method throwing UOE
     *
     * @throws UnsupportedOperationException : throws this exception when the method is called
     */
    @Override
    public final int hashCode() {

        throw new UnsupportedOperationException();
    }

    /**
     * CartesianCoordinates overrode method throwing UOE
     *
     * @throws UnsupportedOperationException : throws this exception when the method is called
     */
    @Override
    public final boolean equals(Object obj) {

        throw new UnsupportedOperationException();
    }

    /**
     * CartesianCoordinates overrode method returning a string with the coordinates
     *
     * @return (String) : the string of coordinates
     */
    @Override
    public final String toString() {

        return String.format(Locale.ROOT, "(abscisse =%.4f°, ordonnée =%.4f°)", x(), y());
    }
}
