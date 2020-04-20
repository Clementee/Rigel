package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

//@FunctionalInterface
public interface TimeAccelerator{
    ZonedDateTime adjust(ZonedDateTime T0, long elapsedTime);

    static TimeAccelerator continuous(int alpha){
        return new TimeAccelerator() {
            @Override
            public ZonedDateTime adjust(ZonedDateTime T0, long elapsedTime) {
                return T0.plusNanos(alpha*elapsedTime);
            }
        };
    }
    static TimeAccelerator discrete(int v, Duration S){
        return new TimeAccelerator() {
            @Override
            public ZonedDateTime adjust(ZonedDateTime T0, long elapsedTime) {
                return T0.plusNanos((long) Math.floor(v*elapsedTime)*S.getNano());
            }
        };
    }

}
