package ch.epfl.rigel.bonus;

import ch.epfl.rigel.astronomy.Asterism;

public class Constellation {
    private final Asterism constellation;
    private final String constellationName;


    public Constellation(String fullName, Asterism asterism) {
        constellationName = fullName;
        this.constellation = asterism;
    }

    /**
     * Constellation method returning the list of stars composing the constellation
     *
     * @return constellation (List<Star>) : return the list of stars composing the constellation
     */
    public Asterism asterism() {
        return constellation;
    }

    /**
     * Constellation method returning the name of the constellation
     *
     * @return (String) constellation
     */

    public String getConstellationName() {return constellationName;}
}
