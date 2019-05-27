
package Application;

import Foundation.DB;
import Technical.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.sql.SQLException;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    //PreparedStatement
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * TextField where the username_TextField is entered.
     */
    @FXML
    private TextField username_TextField;

    /**
     * TextField where the password is entered.
     */
    @FXML
    private TextField password_TextField;

    /**
     * Button created to start the search for login match and to start the connection to database .
     */
    @FXML
    private Button login_Button;


    /**
     * Check the username and the password TextField was entered from the login scene and start
     * the connection to database .
     */

    @FXML
    public void handelLogin(ActionEvent LoginEvent) throws Exception {
        //Start the connection to DB.
        Connection con = DB.connect();

        //Create an object from JBDC class to read the user name and the password from the database .
        JDBC login = new JDBC();
        login.loginTSQL();

        //Text field read the username and password .
        String userName = username_TextField.getText().trim();
        String password = password_TextField.getText().trim();


        try {
            //Start the JDBC read query and read the loginTSQL_query
            preparedStatement = con.prepareStatement(login.loginTSQL());
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();

            //Check for match from text fields and the date from database.
            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Username or password is wrong" );
            }
            else {
                JOptionPane.showMessageDialog(null, "Welcome " + userName);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/main.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

    }