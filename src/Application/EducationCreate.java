package Application;

import Foundation.DB;
import Technical.JDBC;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class EducationCreate {

    PreparedStatement preparedStatement = null;

    @FXML
    private TextField amu_TextField;

    @FXML
    private TextField title_TextField;

    @FXML
    private TextField provider_TextField;

    @FXML
    private TextField number_of_Days_TextField;

    @FXML
    private TextField information_TextField;

    @FXML
    private TextField type_TextField;


    @FXML
    public void addEducation(){

        //Text field read the javafx fields .
        String amu = amu_TextField.getText().trim();
        String title = title_TextField.getText().trim();
        String provider = provider_TextField.getText().trim();
        String number_of_Days = number_of_Days_TextField.getText().trim();
        String information = information_TextField.getText().trim();
        String type = type_TextField.getText().trim();



        //Start the connection to DB.
        Connection con = DB.connect();

        //Create an object from JBDC class
        JDBC create_Edu = new JDBC();
        create_Edu.CreateEducationTSQL();

        if (amu.equals("") || title.equals("") || provider.equals("")  || number_of_Days.equals("") || information.equals("") || type.equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all fields, some are empty");

        } else {
            DB.selectSQL("SELECT fld_AMU from tbl_Educations where fld_AMU = '" + amu + "'");
            String un = DB.getData();

            if (un.equals(amu.toLowerCase())){
                JOptionPane.showMessageDialog(null, "AMU already exist");
            }
            else{
                //SQL statement
                //DB.insertSQL("INSERT INTO tbl_Educations VALUES('" + amu + "', '" + title + "', '" + provider + "' , '" + number_of_Days + "', '" + information + "')");

                try {
                    //Start the JDBC
                    preparedStatement = con.prepareStatement(create_Edu.CreateEducationTSQL());
                    preparedStatement.setInt(1,Integer.parseInt(amu));
                    preparedStatement.setString(2,title);
                    preparedStatement.setString(3,provider);
                    preparedStatement.setInt(4,Integer.parseInt(number_of_Days));
                    preparedStatement.setString(5,information);
                    preparedStatement.setString(6,type);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Create Done" );

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Wrong with creating" );
                    e.printStackTrace();
                }
            }
        }
    }
}
