package Application;


import Domain.EducationViewTable;
import Foundation.DB;
import Technical.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class EducationView implements  Initializable{
    Connection con = DB.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


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
}
