package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

//@FunctionalInterface
public interface TimeAccelerator{
    ZonedDateTime adjust(ZonedDateTime T0, long elapsedTime);

    static TimeAccelerator continuous(int alpha){
        return (alpha)->;
    }
    static TimeAccelerator discrete(int v, Duration S){
        return null;
    }

}
