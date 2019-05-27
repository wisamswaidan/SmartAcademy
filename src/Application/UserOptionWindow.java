package Application;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.*;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.StageStyle;
import java.io.*;

public class UserOptionWindow {



    @FXML
    private Button createUser;

    @FXML
    private void openUserView(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("EASV");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

}
