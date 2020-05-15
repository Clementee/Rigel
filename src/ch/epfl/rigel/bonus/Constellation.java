package ch.epfl.rigel.bonus;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.Star;

import java.util.List;

import static ch.epfl.rigel.Preconditions.checkArgument;

public class Constellation {
    private final List<Star> constellation;
    private final String constellationName;


    public Constellation(String name, List<Star> stars) {
        constellationName = name;
        this.constellation = stars;
    }

    /**
     * Constellation method returning the list of stars composing the constellation
     *
     * @return constellation (List<Star>) : return the list of stars composing the constellation
     */
    public List<Star> stars() {
        return constellation;
    }

    /**
     * Constellation method returning the name of the constellation
     *
     * @return (String) constellation
     */

    public String getConstellationName() {return constellationName;}
}
