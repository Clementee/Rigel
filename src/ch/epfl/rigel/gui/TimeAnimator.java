package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;


public final class TimeAnimator extends AnimationTimer {

    private DateTimeBean instantObservation;

    public TimeAnimator(DateTimeBean dateTimeBean){
        instantObservation = dateTimeBean;
    }

    @Override
    public void handle(long l) {

    }
}
