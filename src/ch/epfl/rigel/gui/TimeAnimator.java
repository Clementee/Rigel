package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;

/**
 * A time animator
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class TimeAnimator extends AnimationTimer {

    private boolean firstRun;
    private long lastTime;

    //à garder ou pas ?
    private long firstTime;

    private final BooleanProperty running = new SimpleBooleanProperty();
    private final ObjectProperty<TimeAccelerator> accelerator = new SimpleObjectProperty<>(null);

    private final DateTimeBean instantObservation;

    /**
     * TimeAnimator public constructor initializing the date time bean we want to modify
     *
     * @param dateTimeBean (DateTimeBean) : gives the date time bean we chose to modify
     */
    public TimeAnimator(DateTimeBean dateTimeBean){
        instantObservation = dateTimeBean;
    }

    /**
     * Time Animator public overridden method start representing the first run and the beginning of the simulation
     */
    @Override
    public void start() {
        super.start();
        running.setValue(true);
        firstRun = true;
    }

    /**
     * Time Animator public overridden method stop representing the end of the simulation
     */
    @Override
    public void stop() {
        super.stop();
        running.set(false);
    }

    /**
     * Time Animator public overridden method handle representing the steps of the simulation,
     * updating the date time bean
     *
     * @param l (long) : giving the number of milliseconds of the time
     */
    @Override
    public void handle(long l) {
        
        if(firstRun){
            firstTime = l;
            lastTime = l;
            firstRun = false;
        }
        
        instantObservation.setZonedDateTime(accelerator.getValue().adjust(instantObservation.getZonedDateTime(), l - lastTime));
        lastTime = l;
    }

    /**
     * TimeAnimator public method returning the accelerator as a property
     *
     * @return accelerator (ObjectProperty<TimeAccelerator>) : return the accelerator as a property
     */
    public ObjectProperty<TimeAccelerator> getAcceleratorProperty(){
        return accelerator;
    }

    /**
     * TimeAnimator public method setting the accelerator with the one entered in parameters
     *
     * @param accelerator (TimeAccelerator) : gives the accelerator of reference
     */
    public void setAccelerator(TimeAccelerator accelerator){
        this.accelerator.set(accelerator);
    }

    /**
     * TimeAnimator public method returning the accelerator
     *
     * @return (TimeAccelerator) : return the accelerator
     */
    public TimeAccelerator getAccelerator(){
        return accelerator.get();
    }

    /**
     * TimeAnimator public method returning the running property
     * 
     * @return running (ReadOnlyBooleanProperty) : return the running property
     */
    public ReadOnlyBooleanProperty runningProperty(){
        return running;
    }

    /**
     * TimeAnimator public method returning the boolean linked to the state of the simulation
     * 
     * @return (boolean) : return the state of the simulation
     */
    public boolean isRunning(){
        return running.get();
    }
}
