package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /**
     * Move to education scene.
     */
    public void ShowEduOptions(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/education.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();
    }
    /**
     * Move to users scene.
     */
    public void ShowUsersOptions(ActionEvent event) throws Exception {

        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/user.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }
    /**
     * Move to company scene.
     */
    public void ShowCompaniesOptions(ActionEvent event) throws Exception {

        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/company.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }
    /**
     * Move to employee scene.
     */
    public void ShowEmployeesOptions(ActionEvent event) throws Exception {

        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/employee.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }
    /**
     * Move to Assign user scene.
     */
    public void ShowAssignUserOptions(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/assignUser.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }
    /**
     * Move to education Schedule scene.
     */
    public void ShowEduSchOptions(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/eduSch.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }
    /**
     * Move to education plan scene.
     */
    public void ShowEduPlanOptions(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/educationPlan.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
