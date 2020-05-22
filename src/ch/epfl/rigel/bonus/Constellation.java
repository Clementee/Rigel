package ch.epfl.rigel.bonus;

import ch.epfl.rigel.astronomy.Asterism;

/**
 * A constellation
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public class Constellation {
    
    private final Asterism constellation;
    private final String constellationName;

    /**
     * Public constellation constructor initializing the constellation
     * 
     * @param fullName (String) : gives the name of the constellation
     * @param asterism (Asterism) : gives the asterism composing the constellation
     */
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
    public String getConstellationName() {
        return constellationName;
    }
}
