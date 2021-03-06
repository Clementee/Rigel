package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

/**
 * A Polynomial function
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Polynomial {
    private final double[] tab;

    /**
     * Polynomial private constructor representing a Polynomial object under the form of an array
     *
     * @param coefficientN (double) : the coefficient of higher degree (not null)
     * @param coefficients (double[] ) : the other coefficients of our polynomial function (can be null)
     */
    private Polynomial(double coefficientN, double[] coefficients) {

        tab = new double[coefficients.length + 1];
        tab[0] = coefficientN;

        System.arraycopy(coefficients, 0, tab, 1, coefficients.length);
    }

    /**
     * Method creating a new Polynomial object
     *
     * @param coefficientN (double) : the coefficient of higher degree (not null)
     * @param coefficients (double[] ) : the other coefficients of our polynomial function (can be null)
     * @return (Polynomial) : the object Polynomial created with the given characteristics
     */
    public static Polynomial of(double coefficientN, double... coefficients) {
        Preconditions.checkArgument(coefficientN != 0);
        return new Polynomial(coefficientN, coefficients);
    }

    /**
     * Method calculating the image of the x by the polynomial, using the Horner form
     *
     * @param x (double ) : the wanted pre-image
     * @return (double) : the image
     */
    public double at(double x) {

        double value = tab[0];

        for (int i = 1; i < tab.length; i++) {
            value = value * x + tab[i];
        }

        return value;
    }

    /**
     * Method printing our Polynomial object as a mathematical function, and not as a table
     *
     * @return (String) : the Polynomial function
     */
    @Override
    public String toString() {

        StringBuilder text = new StringBuilder();

        for (int i = 0; i < tab.length; i++) {
            if (i == 0) {
                if (tab.length == 1) {
                    text.append(tab[i]);
                } else {
                    if (tab.length == 2) {
                        if (tab[i] != 1 && tab[i] != -1)
                            text.append(tab[i]).append("x");
                        else
                            text.append(tab[i] == 1 ? "x" : "-x");
                    } else {
                        if (tab[i] != 1 && tab[i] != -1)
                            text.append(tab[i]).append("x^").append(tab.length - 1);
                        else
                            text.append(tab[i] == 1 ? "x^" : "-x^   ").append(tab.length - 1);
                    }
                }
            } else {
                if (tab[i] != 0) {
                    if (i == tab.length - 1)
                        text.append(tab[i] > 0 ? "+" : "").append(tab[i]);
                    else if (i == tab.length - 2)
                        text.append(tab[i] > 0 ? "+" : "").append(tab[i]).append("x");
                    else if (tab[i] != 1)
                        text.append(tab[i] > 0 ? "+" : "").append(tab[i]).append("x^").append(tab.length - i - 1);
                    else
                        text.append(tab[i] == 1 ? "+x^" : "-x^").append(tab.length - i - 1);

                }
            }
        }
        return text.toString();
    }


    /**
     * Method throwing an UnsupportedOperationException (UOE)
     *
     * @return (int) : nothing because an error has already been thrown
     * @throws UnsupportedOperationException :
     */
    @Override
    public int hashCode() {
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
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}
