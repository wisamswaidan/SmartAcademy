package Application;


import Domain.EducationViewTable;
import Foundation.DB;
import Technical.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EducationView implements  Initializable{
    Connection con = DB.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Button removeBut;

    @FXML
    private TableView <EducationViewTable> table;
    @FXML
    private TableColumn <EducationViewTable, String> col_amu;
    @FXML
    private TableColumn <EducationViewTable, String> col_title;
    @FXML
    private TableColumn <EducationViewTable, String> col_provier;
    @FXML
    private TableColumn <EducationViewTable, String> col_numofday;
    @FXML
    private TableColumn <EducationViewTable, String> col_type;
    @FXML
    private TableColumn <EducationViewTable, String> col_info;

    ObservableList<EducationViewTable> oblist = FXCollections.observableArrayList();
    List<String> mainPlaylists = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JDBC viewEducations = new JDBC();
        viewEducations.ViewEducationTSQL();

        try {

            preparedStatement = con.prepareStatement(viewEducations.ViewEducationTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                oblist.add(new EducationViewTable(

                        resultSet.getString("fld_AMU"),
                        resultSet.getString("fld_Title"),
                        resultSet.getString("fld_Provider"),
                        resultSet.getString("fld_NumOfDays"),
                        resultSet.getString("fld_Type"),
                        resultSet.getString("fld_Information")));
                        mainPlaylists.add(resultSet.getString("fld_AMU"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_amu.setCellValueFactory(new PropertyValueFactory<>("AMU"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        col_provier.setCellValueFactory(new PropertyValueFactory<>("Provider"));
        col_numofday.setCellValueFactory(new PropertyValueFactory<>("NumOfDays"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_info.setCellValueFactory(new PropertyValueFactory<>("information"));

        table.setItems(oblist);

    }


    @FXML
    public void removeEdu(){
        //Create an object for selection cells...
        TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        EducationViewTable item = table.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        System.out.println(data);


        //JBDC
        JDBC deleteEducations = new JDBC();
        deleteEducations.ViewEducationTSQL();

        //Check Method if the user select the correct column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0 ; i < mainPlaylists.size() ; i++){

            String amuSearchMatch = mainPlaylists.get(i);
            if (data.equals(amuSearchMatch)){
                //System.out.println("match found " + amuSearchMatch);
                amuSearchMatch = resultMatch;
                matchBoolena = true;

                if(matchBoolena == true){
                    try {
                        preparedStatement = con.prepareStatement(deleteEducations.DeleteEducationTSQL());
                        preparedStatement.setString(1,resultMatch);
                        //We use executeUpdate() instead of executeQuery() because we dont expect any return .
                        preparedStatement.executeUpdate();
                        //System.out.println("Delete Done for " + chooseAMU);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(matchBoolena == false){
            JOptionPane.showMessageDialog(null, "Please choose AMU column " );
        }
        else
            JOptionPane.showMessageDialog(null, "Delete Done" );

    }

}
