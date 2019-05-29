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


public class EducationView {
    //Start the connection to DB.
    Connection con = DB.connect();

    //PreparedStatement
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    private Button buttonView;

    @FXML
    private ListView listViewtest;

    public void viewEduListView(){
        //Create the Array List for the leftListView.
         List<String> allSongsList = new ArrayList<>();
         ListProperty<String> allSongslistProperty = new SimpleListProperty<>();


        //Create an object from JBDC class to view the educations from the database .
        JDBC viewEducations = new JDBC();
        viewEducations.ViewEducationTSQL();

        try {

            //Start the JDBC read query and view the educations from database
            preparedStatement = con.prepareStatement(viewEducations.ViewEducationTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fld_AMU = resultSet.getString("fld_AMU");
                String fld_Title = resultSet.getString("fld_Title");
                String fld_Provider = resultSet.getString("fld_Provider");
                String fld_NumOfDays = resultSet.getString("fld_NumOfDays");
                String fld_Type = resultSet.getString("fld_Type");
                String fld_Information = resultSet.getString("fld_Information");

                System.out.println("AMU : " + fld_AMU);
                System.out.println("Title : " + fld_Title);
                System.out.println("Provider : " + fld_Provider);
                System.out.println("NumOfDays : " + fld_NumOfDays);
                System.out.println("Type : " + fld_Type);
                System.out.println("Information : " + fld_Information);
                String AllEducation  = "AMU : "+ fld_AMU + " " + "Title : "+ fld_Title + " " + "Provider : "+ fld_Provider +" " + "NumOfDays : "+ fld_NumOfDays+ " " + "Type : "
                        + fld_Type+ " " + "Information : " + fld_Information;
                allSongsList.add(AllEducation);

                listViewtest.itemsProperty().bind(allSongslistProperty);
                allSongslistProperty.set(FXCollections.observableArrayList(allSongsList));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
