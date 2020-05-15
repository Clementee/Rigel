package ch.epfl.rigel.bonus;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.Star;

import java.util.List;

import static ch.epfl.rigel.Preconditions.checkArgument;

public class Constellation {
    private final Asterism constellation;
    private final String constellationName;


    public Constellation(String name, Asterism constellation) {
        constellationName = name;
        this.constellation = constellation;
    }

    /**
     * Constellation method returning the list of stars composing the constellation
     *
     * @return constellation (List<Star>) : return the list of stars composing the constellation
     */
    public Asterism asterims() {
        return constellation;
    }

    /**
     * Constellation method returning the name of the constellation
     *
     * @return (String) constellation
     */

    public String getConstellationName() {return constellationName;}
}
