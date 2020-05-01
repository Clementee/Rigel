package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.SQLOutput;

/**
 * A viewing parameters bean
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public class ViewingParametersBean {

    private final ObjectProperty<Double> fieldOfViewDegProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<HorizontalCoordinates> centerProperty = new SimpleObjectProperty<>();


    public ObjectProperty<Double> fieldOfViewDegProperty(){
        return fieldOfViewDegProperty;
    }

    public void setFieldOfViewDeg(double fieldOfView){
        fieldOfViewDegProperty.set(fieldOfView);
    }

    public Double getFieldOfViewDeg(){
        return fieldOfViewDegProperty.get();
    }


    public ObjectProperty<HorizontalCoordinates> centerProperty(){
        return centerProperty;
    }

    public void setCenter(HorizontalCoordinates centerCoords){
        centerProperty.set(centerCoords);
    }

    public HorizontalCoordinates getCenter(){
        return centerProperty.get();
    }
}