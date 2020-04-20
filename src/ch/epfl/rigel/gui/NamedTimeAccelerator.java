package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;

import static ch.epfl.rigel.gui.TimeAccelerator.continuous;
import static ch.epfl.rigel.gui.TimeAccelerator.discrete;

public enum NamedTimeAccelerator {
    TIMES_1("1×", continuous(1)), TIMES_30("30×", continuous(30)),
    TIMES_300("300×", continuous(300)), TIMES_3000("3000×",continuous(3000)),
    DAY("jour", discrete(60, Duration.ofDays(1))), SIDEREAL_DAYS("jour sidéral", discrete(60, Duration.ofSeconds(23*3600+56*60+4)));
    //private static int SIDEREAL_DAYS_IN_SECONDS = 23*3600+56*60+4;

    private String frenchName;
    private TimeAccelerator accelerator;
    NamedTimeAccelerator(String name, TimeAccelerator accelerator){
        frenchName = name;
        this.accelerator = accelerator;
    }

    public String getName(){
        return frenchName;
    }

    public TimeAccelerator getAccelerator() {
        return accelerator;
    }

    @Override
    public String toString() {
        return frenchName;
    }
}
