package Application;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;

public class Test extends Control {
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                "First", "Second", "Third")
        );
}
