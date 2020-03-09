package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * The sun
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Sun extends CelestialObject{
    private final static String sunName = "Soleil";
    private static float sunPhase;
    private final static float magnitude = -26.7f;
    private EclipticCoordinates eclipticPos;
    private float meanAnomaly;

    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly){
        super(sunName, equatorialPos, angularSize, magnitude);
        this.meanAnomaly = meanAnomaly;
        this.eclipticPos=Objects.requireNonNull(eclipticPos);

        if(eclipticPos.lon()==0 & eclipticPos.lat() ==0){
            throw new NullPointerException();
        }
    }

    public EclipticCoordinates eclipticPos(){
        return eclipticPos;
    }

    public double meanAnomaly(){
        return meanAnomaly;
    }
}
