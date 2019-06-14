
package Application;

import Foundation.DB;
import Technical.StoredProcedures;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.sql.*;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class LoginController  {

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
    public void handelLogin(ActionEvent event) throws Exception {

        //Start the connection to DB.
        Connection con = DB.connect();

        //Text field read the username and password .
        String userName = username_TextField.getText().trim();
        String password = password_TextField.getText().trim();

        //Start the StoredProcedures
        StoredProcedures.checkLogin();
        ResultSet rs;


        try (CallableStatement stmt = con.prepareCall(StoredProcedures.checkLogin())){

            stmt.setString(1,userName);
            stmt.setString(2,password);
            rs = stmt.executeQuery();

            //Check for match from text fields and the date from database.
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Username or password is wrong" );
            }
            else {
                JOptionPane.showMessageDialog(null, "Welcome " + userName);
                Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/main.fxml"));
                Scene showScene = new Scene(showPage);
                Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
                showApp.setScene(showScene);
                showApp.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

    }