package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;


/**
 * The moon
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Moon extends CelestialObject {

    private static float moonPhase;
    private final static String moonName = "Lune";

    // possiblement à corriger
    Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {

        super(moonName, equatorialPos, angularSize, magnitude);

        if (!(ClosedInterval.of(0, 1).contains(phase))) {
            throw new IllegalArgumentException();
        }

        else {
            moonPhase = phase*100;
        }
    }

    @Override
    public String info(){
        return moonName + " (" +String.format(Locale.ROOT,"(%.1f)",moonPhase)+ "%)";
    }
}
