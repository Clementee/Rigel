package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.*;

public final class DateTimeBean {

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(null);
    private ObjectProperty<LocalTime> time = new SimpleObjectProperty<>(null);
    private ObjectProperty<ZoneId> zone = new SimpleObjectProperty<>(null);

    public ObjectProperty<LocalDate> dateProperty(){
        return date;
    }

    public LocalDate getDate(){
        return date.getValue();
    }

    public void setDate(LocalDate date){
        this.date
                .setValue(date);
    }

    public ObjectProperty<LocalTime> timeProperty(){
        return time;
    }

    public LocalTime getTime(){
        return time.getValue();
    }

    public void setTime(LocalTime time){
        this.time.setValue(time);
    }

    public ObjectProperty<ZoneId> zoneProperty(){
        return zone;
    }

    public ZoneId getZone(){
        return zone.getValue();
    }

    public void setZone(ZoneId zone){
        this.zone
                .setValue(zone);
    }

    public ZonedDateTime getZonedDateTime(){
        LocalDateTime localDateTime = date.getValue().atTime(time.getValue());
        return localDateTime.atZone(zone.getValue());
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime){

        setZone(zonedDateTime.getOffset().normalized());
        setTime(zonedDateTime.toLocalTime());
        setDate(zonedDateTime.toLocalDate());
    }
}
