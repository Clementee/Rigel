package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.*;

/**
 * A date-time bean
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class DateTimeBean {

    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalTime> timeProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<ZoneId> zoneProperty = new SimpleObjectProperty<>(null);

    /**
     * DateTimeBean public method returning the date as a property
     * 
     * @return dateProperty (ObjectProperty<LocalDate>) : the date property
     */
    public ObjectProperty<LocalDate> dateProperty(){
        return dateProperty;
    }

    /**
     * DateTimeBean public method returning the local date
     *
     * @return (LocalDate) : returning the date
     */
    public LocalDate getDate(){
        return dateProperty.getValue();
    }

    /**
     * DateTimeBean public method used to set the date to the one entered in parameters
     * 
     * @param date (LocalDate) : gives the date we want to settle to
     */
    public void setDate(LocalDate date){
        this.dateProperty
                .setValue(date);
    }

    /**
     * DateTimeBean public method returning the time as a property
     *
     * @return timeProperty (ObjectProperty<LocalTime>) : returning the time property
     */
    public ObjectProperty<LocalTime> timeProperty(){
        return timeProperty;
    }

    /**
     * DateTimeBean public method returning the local time
     *
     * @return (LocalTime) : returning the time
     */
    public LocalTime getTime(){
        return timeProperty.getValue();
    }

    /**
     * DateTimeBean public method used to set the time to the one entered in parameters
     *
     * @param time (LocalTime) : gives the time we want to settle to
     */
    public void setTime(LocalTime time){
        this.timeProperty
                .setValue(time);
    }
    
    /**
     * DateTimeBean public method returning the zone as a property
     *
     * @return zoneProperty (ObjectProperty<ZoneId>) : the zone property
     */
    public ObjectProperty<ZoneId> zoneProperty(){
        return zoneProperty;
    }

    /**
     * DateTimeBean public method returning the local zone
     *
     * @return (ZoneId) : returning the zone
     */
    public ZoneId getZone(){
        return zoneProperty.getValue();
    }

    /**
     * DateTimeBean public method used to set the zone to the one entered in parameters
     *
     * @param zoneProperty (ZoneId) : gives the zone we want to settle to
     */
    public void setZone(ZoneId zoneProperty){
        this.zoneProperty
                .setValue(zoneProperty);
    }

    /**
     * DateTimeBean public method returning the local zonedDateTime
     *
     * @return (ZonedDateTime) : returning the zonedDateTime created with the parameters entered
     */
    public ZonedDateTime getZonedDateTime(){
        return ZonedDateTime.of(getDate(), getTime(), getZone());
    }

    /**
     * DateTimeBean public method used to set the zonedDateTime to the one entered in parameters 
     * and set the properties with its components
     *
     * @param zonedDateTime (ZonedDateTime) : gives the zonedDateTime we want to initialize the properties with 
     */
    public void setZonedDateTime(ZonedDateTime zonedDateTime){

        setZone(zonedDateTime.getZone());
        setTime(zonedDateTime.toLocalTime());
        setDate(zonedDateTime.toLocalDate());
    }
}
