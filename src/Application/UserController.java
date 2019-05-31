package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserController {
    //PreparedStatement
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void handelCreate(ActionEvent LoginEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/createUser.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void handelUserView(ActionEvent LoginEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/userView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void handelEdit(ActionEvent LoginEvent) throws Exception {}
    public void handelDelete(ActionEvent LoginEvent) throws Exception {}


}
