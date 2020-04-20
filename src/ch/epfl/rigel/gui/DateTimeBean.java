package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;

import java.time.*;

public final class DateTimeBean {

    private ObjectProperty<LocalDate> date = null;
    private ObjectProperty<LocalTime> time = null;
    private ObjectProperty<ZoneId> zone = null;
    private LocalDateTime localDateTime;

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
        this.time
                .setValue(time);
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
        localDateTime = date.getValue().atTime(time.getValue());
        return localDateTime.atZone(zone.getValue());
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime){
        zone.setValue(zonedDateTime.getOffset().normalized());
        time.setValue(zonedDateTime.toLocalTime());
        date.setValue(zonedDateTime.toLocalDate());
    }
}
