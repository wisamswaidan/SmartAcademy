package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainController {
    //PreparedStatement
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void ShowEduOptions(ActionEvent LoginEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/education.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void ShowUsersOptions(ActionEvent LoginEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/user.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void ShowCompaniesOptions(ActionEvent LoginEvent) throws Exception {}
    public void ShowEmployeesOptions(ActionEvent LoginEvent) throws Exception {}
    public void ShowAssignUserOptions(ActionEvent LoginEvent) throws Exception {}
    public void ShowEduSchOptions(ActionEvent LoginEvent) throws Exception {}
    public void ShowEduPlanOptions(ActionEvent LoginEvent) throws Exception {}


}
