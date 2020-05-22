package ch.epfl.rigel.gui;

import java.time.Duration;

/**
 * Enumeration of named timeAccelerator
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum NamedTimeAccelerator {

    //enum of all the timeAccelerators used
    TIMES_1("1x", TimeAccelerator.continuous(1)),
    TIMES_30("30x",TimeAccelerator.continuous(30)),
    TIMES_300("300x",TimeAccelerator.continuous(300)),
    TIMES_3000("3000x", TimeAccelerator.continuous(3000)),
    DAY("jour",TimeAccelerator.discrete(60, Duration.parse("PT24H"))),
    SIDEREAL_DAY("jour sidéral",TimeAccelerator.discrete(60,Duration.parse("PT23H56M4S")));

    private final String name;
    private final TimeAccelerator accelerator;

    /**
     * NamedTimeAccelerator package private constructor initializing its name and the accelerator
     *
     * @param name (String) : gives the name we want to initialize
     * @param accelerator (TimeAccelerator) : gives the accelerator we use
     */
    NamedTimeAccelerator(String name, TimeAccelerator accelerator){
        this.name = name;
        this.accelerator = accelerator;
    }

    /**
     * NamedTimeAccelerator public method returning the name
     *
     * @return name (String) : return the name
     */
    public String getName(){
        return name;
    }

    /**
     * NamedTimeAccelerator public method returning the accelerator
     *
     * @return accelerator (TimeAccelerator) : return the accelerator
     */
    public TimeAccelerator getAccelerator(){
        return accelerator;
    }

    /**
     * NamedTimeAccelerator public and overridden method returning the name
     *
     * @return (String) : return the name
     */
    @Override
    public String toString(){
        return getName();
    }
}
