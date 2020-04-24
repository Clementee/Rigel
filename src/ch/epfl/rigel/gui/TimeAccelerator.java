package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * A time accelerator
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
@FunctionalInterface
public interface TimeAccelerator{

    /**
     * TimeAccelerator abstract method adjust
     *
     * @param T0 (ZonedDateTime) : gives the initial simulated time
     * @param elapsedTime (long) : gives the elapsed time since the beginning
     */
    ZonedDateTime adjust(ZonedDateTime T0, long elapsedTime);

    /**
     * TimeAccelerator static method creating a continuous accelerator with factor alpha
     *
     * @param alpha (int) ; gives tbe factor of the acceleration
     * @return (TimeAccelerator) : return a continuous accelerator
     */
    static TimeAccelerator continuous(int alpha){
        return (T0, elapsedTime) -> T0.plusNanos(alpha*elapsedTime);
    }

    /**
     * TimeAccelerator static method creating a discrete accelerator with advancement frequency and the step
     *
     * @param v (int) ; gives tbe advancement frequency
     * @param S (Duration) : gives the step S
     *
     * @return (TimeAccelerator) : return a discrete accelerator
     */
    static TimeAccelerator discrete(int v, Duration S){
        return (T0, elapsedTime) -> T0.plusNanos((long) Math.floor(v*elapsedTime)*S.getNano());
    }
}
