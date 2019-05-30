package Application;
import Foundation.DB;
import Technical.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Create_User {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private TextField phone_TextField;

    @FXML
    private TextField address_TextField;

    @FXML
    private TextField zipcode_TextField;

    @FXML
    private TextField username_TextField;

    @FXML
    private ChoiceBox<?> accessBox;

    @FXML
    private TextField lastname_TextField;

    @FXML
    private TextField password_TextField;

    @FXML
    private TextField firstname_TextField;

    @FXML
    public void addUser(ActionEvent event) throws Exception{
        Connection con = DB.connect();

        //Create an object from JBDC class to read the user name and the password from the database .
        JDBC checkUser = new JDBC();
        checkUser.CheckUserTSQL();

        //Text field read the username and phone to check for duplications.
        String phone = phone_TextField.getText().trim();
        String username = username_TextField.getText().trim();


        try {
            //Start the JDBC read query and read the loginTSQL_query
            preparedStatement = con.prepareStatement(checkUser.CheckUserTSQL());
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            //Check for match from text fields and the date from database.
            if (!resultSet.next()) {
                //JOptionPane.showMessageDialog(null, "Username or password is wrong" );
            }
            else {

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}