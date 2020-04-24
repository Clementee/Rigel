package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.*;

public final class DateTimeBean {

    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalTime> timeProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<ZoneId> zoneProperty = new SimpleObjectProperty<>(null);

    public ObjectProperty<LocalDate> dateProperty(){
        return dateProperty;
    }

    public LocalDate getDate(){
        return dateProperty.getValue();
    }

    public void setDate(LocalDate date){
        this.dateProperty
                .setValue(date);
    }

    public ObjectProperty<LocalTime> timeProperty(){
        return timeProperty;
    }

    public LocalTime getTime(){
        return timeProperty.getValue();
    }

    public void setTime(LocalTime time){
        this.timeProperty.setValue(time);
    }

    public ObjectProperty<ZoneId> zonePropertyProperty(){
        return zoneProperty;
    }

    public ZoneId getZone(){
        return zoneProperty.getValue();
    }

    public void setZone(ZoneId zoneProperty){
        this.zoneProperty
                .setValue(zoneProperty);
    }

    public ZonedDateTime getZonedDateTime(){
        return ZonedDateTime.of(getDate(), getTime(), getZone());
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime){

        setZone(zonedDateTime.getZone());
        setTime(zonedDateTime.toLocalTime());
        setDate(zonedDateTime.toLocalDate());
    }
}
