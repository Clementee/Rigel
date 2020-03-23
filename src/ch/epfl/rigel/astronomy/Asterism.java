package ch.epfl.rigel.astronomy;

import java.util.List;


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
    Asterism(List<Star> stars){

        if(!stars.isEmpty()){
            asterismList = stars;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Asterism method returning the list of stars composing the asterism
     *
     * @return asterismList (List<Star>) : return the list of stars composing the astersim
     */
    List<Star> stars(){
        return List.copyOf(asterismList);
    }
}
