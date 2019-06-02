package Application.Education_Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EducationController {

    public void handelCreate(ActionEvent Event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Education_FXML/education_create.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handelView(ActionEvent Event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Education_FXML/education_view.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
