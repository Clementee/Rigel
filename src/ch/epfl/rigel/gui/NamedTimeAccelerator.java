package ch.epfl.rigel.gui;

import java.time.Duration;

/**
 * Enumeration of named timeAccelerator
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum NamedTimeAccelerator {

    TIMES_ONE("1x", TimeAccelerator.continuous(1)),
    TIMES_30("30x",TimeAccelerator.continuous(30)),
    TIMES_300("300x",TimeAccelerator.continuous(300)),
    TIMES_3000("3000x", TimeAccelerator.continuous(3000)),
    DAY("jour",TimeAccelerator.discrete(60, Duration.ofDays(1))),
    SIDEREAL_DAY("jour sidéral",TimeAccelerator.discrete(60,Duration.ofSeconds(23*3600+54*60+4)));

    private final String name;
    private final TimeAccelerator accelerator;

    NamedTimeAccelerator(String name, TimeAccelerator accelerator){
        this.name = name;
        this.accelerator = accelerator;
    }

    public String getName(NamedTimeAccelerator namedTimeAccelerator){
        return namedTimeAccelerator.name;
    }

    public TimeAccelerator getAccelerator(NamedTimeAccelerator namedTimeAccelerator){
        return namedTimeAccelerator.accelerator;
    }

    @Override
    public String toString(){
        return name;
    }
}
