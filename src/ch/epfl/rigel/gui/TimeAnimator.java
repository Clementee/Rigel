package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;


public final class TimeAnimator extends AnimationTimer {

    private boolean firstRun;
    private long lastTime;

    private final BooleanProperty running = new SimpleBooleanProperty();
    private final ObjectProperty<TimeAccelerator> accelerator = new SimpleObjectProperty<>(null);

    private DateTimeBean instantObservation;

    public TimeAnimator(DateTimeBean dateTimeBean){
        instantObservation = dateTimeBean;
    }

    @Override
    public void start() {
        super.start();
        running.setValue(true);
        firstRun = true;
    }

    @Override
    public void stop() {
        running.set(false);
        super.stop();
    }

    @Override
    public void handle(long l) {
        if(firstRun){
            lastTime = l;
            firstRun = false;
        }
        instantObservation.setZonedDateTime(accelerator.getValue().adjust(instantObservation.getZonedDateTime(), l - lastTime));
        lastTime = l;
    }

    public ObjectProperty<TimeAccelerator> getAcceleratorProperty(){
        return accelerator;
    }

    public void setAccelerator(TimeAccelerator accelerator){
        this.accelerator.set(accelerator);
    }

    public TimeAccelerator getAccelerator(){
        return accelerator.get();
    }

    public ReadOnlyBooleanProperty runningProperty(){
        return running;
    }

    public boolean isRunning(){
        return running.get();
    }
}
