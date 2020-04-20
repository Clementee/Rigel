package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

//@FunctionalInterface
public interface TimeAccelerator {
    ZonedDateTime adjust(ZonedDateTime T0, long elapsedTime);

    static NamedTimeAccelerator continuous(int alpha){
        return null;
    }
    static NamedTimeAccelerator discrete(int v, Duration S){
        return null;
    }

}
