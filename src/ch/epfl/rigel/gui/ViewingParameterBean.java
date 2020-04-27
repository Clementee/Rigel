package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ViewingParameterBean {
    private final ObjectProperty<Double> fieldOfViewDeg  = new SimpleObjectProperty<>(null);
    private final ObjectProperty<HorizontalCoordinates> center = new SimpleObjectProperty<>(null);

    public ObjectProperty<Double> fieldOfViewDegProperty(){return fieldOfViewDeg;}

    public Double getFieldOfViewDeg() {return fieldOfViewDeg.getValue() ;}

    public void setFieldOfViewDeg(double v){this.fieldOfViewDeg.setValue(v);}


    public ObjectProperty<HorizontalCoordinates>  centerProperty(){return center;}

    public HorizontalCoordinates getCenter(){return center.getValue();}

    public void setCenter(HorizontalCoordinates v){this.center.setValue(v);}
}
