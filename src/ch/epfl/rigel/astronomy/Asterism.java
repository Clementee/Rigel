package ch.epfl.rigel.astronomy;

import java.util.Collections;
import java.util.List;

import static ch.epfl.rigel.Preconditions.checkArgument;


/**
 * An asterism
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Asterism {

    private List<Star> asterismList;

    /**
     * Asterism package-private constructor initializing the asterism with the stars composing it
     *
     * @param stars   (List<Star>) : gives a list of stars present in the asterism
     */
    public Asterism(List<Star> stars){
        
        checkArgument(!stars.isEmpty());
        
        asterismList = List.copyOf(stars);
        
    }

    /**
     * Asterism method returning the list of stars composing the asterism
     *
     * @return asterismList (List<Star>) : return the list of stars composing the astersim
     */
    public List<Star> stars(){
        return Collections.unmodifiableList(asterismList);
    }
}
