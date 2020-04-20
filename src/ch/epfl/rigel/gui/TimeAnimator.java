package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;

public final class TimeAnimator extends AnimationTimer {

    private long initialTime;
    private long deltaTime;
    private boolean firstRun;

    private BooleanProperty running = new SimpleBooleanProperty(false);
    private ObjectProperty<TimeAccelerator> accelerator = new SimpleObjectProperty<>(null);

    private DateTimeBean instantObservation = new DateTimeBean();

    public TimeAnimator(DateTimeBean dateTimeBean){
        instantObservation.setZonedDateTime(dateTimeBean.getZonedDateTime());
    }

    @Override
    public void start() {
        running.setValue(true);
        super.start();
        firstRun = true;
    }

    @Override
    public void stop() {
        running.setValue(false);
        super.stop();
    }

    @Override
    public void handle(long l) {
        if(firstRun){
            initialTime = l;
            firstRun = false;
            System.out.println("Beg");
        }
            deltaTime = l - initialTime;
        System.out.println("oof");
    }

    public ObjectProperty<TimeAccelerator> getAcceleratorProperty(){
        return accelerator;
    }

    public void setAccelerator(TimeAccelerator accelerator){
        this.accelerator.setValue(accelerator);
    }

    public TimeAccelerator getAccelerator(){
        return accelerator.getValue();
    }

    public ReadOnlyBooleanProperty getRunningProperty(){
        return running;
    }

    public boolean getRunning(){
        return running.getValue();
    }
}
