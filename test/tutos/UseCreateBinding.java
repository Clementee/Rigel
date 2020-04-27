package tutos;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

public final class UseCreateBinding {
    private static StringProperty s = new SimpleStringProperty("Hi!");
    private static IntegerProperty b = new SimpleIntegerProperty(0);
    private static IntegerProperty e = new SimpleIntegerProperty(3);
    private static ObservableStringValue ss = test();

    private static ObservableStringValue test(){
        ObservableStringValue test = Bindings.createStringBinding(
                () -> s.getValue().substring(b.get(), e.get()),
                s, b, e);
        test.addListener(o -> System.out.println(test.get()));
        return test;
    }
    public static void main(String[] args) {

        System.out.println("----");
        s.set("Hello, world!");
        s.setValue("Bonjour, monde !");
        e.set(16);
        b.set(9);
        System.out.println("----");
    }
}