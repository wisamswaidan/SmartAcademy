package Application;


import Foundation.DB;
import Technical.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.sql.SQLException;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.*;


public class User_View {
    //Start the connection to DB.
    Connection con = DB.connect();

    //PreparedStatement
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    private Button buttonView;

    @FXML
    private ListView listViewUser;

    public void viewUserList(){
        //Create the Array List for the leftListView.
        List<String> allSongsList = new ArrayList<>();
        ListProperty<String> allSongslistProperty = new SimpleListProperty<>();


        //Create an object from JBDC class to view the educations from the database .
        JDBC viewUser = new JDBC();
        viewUser.ViewUserTSQL();

        try {

            //Start the JDBC read query and view the educations from database
            preparedStatement = con.prepareStatement(viewUser.ViewUserTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fld_FirstName = resultSet.getString("fld_FirstName");
                String fld_LastName = resultSet.getString("fld_LastName");
                String fld_TelephoneNumber = resultSet.getString("fld_TelephoneNumber");
                String fld_Address = resultSet.getString("fld_Address");
                String fld_Zipcode = resultSet.getString("fld_ZipCode");
                String fld_UserName = resultSet.getString("fld_UserName");
                String fld_UserType = resultSet.getString("fld_UserType");

                System.out.println("AMU : " + fld_FirstName);
                System.out.println("Title : " + fld_LastName);
                String AllEducation  = "Firstname : "+ fld_FirstName + " " + "Lastname : "+ fld_LastName + " " + "Phone : "+ fld_TelephoneNumber +" " + "Address : "+ fld_Address+ " " + "Zipcode : "
                        + fld_Zipcode+ " " + "Username : " + fld_UserName + " " + "Usertype :" + fld_UserType;
                allSongsList.add(AllEducation);

                listViewUser.itemsProperty().bind(allSongslistProperty);
                allSongslistProperty.set(FXCollections.observableArrayList(allSongsList));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
