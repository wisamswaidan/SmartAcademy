package Application;

import Domain.EducationViewTable;
import Foundation.DB;
import Technical.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.*;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class User_View implements Initializable{


    //Start the connection to DB.
    Connection con = DB.connect();

    //PreparedStatement
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private TableView<EducationViewTable> tableUserView;

    @FXML
    private TableColumn<EducationViewTable, String> col_username;

    @FXML
    private TableColumn<EducationViewTable, String> col_address;

    @FXML
    private TableColumn<EducationViewTable, String> col_name;

    @FXML
    private TableColumn<EducationViewTable, String> col_phone;

    @FXML
    private TableColumn<EducationViewTable, String> col_usertype;

    @FXML
    private TableColumn<EducationViewTable, String> col_password;

    ObservableList<EducationViewTable> oblist1 = FXCollections.observableArrayList();
    List<String> mainPlaylists = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JDBC viewUserList = new JDBC();
        viewUserList.ViewUserTSQL();

        try {

            preparedStatement = con.prepareStatement(viewUserList.ViewUserTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                boolean add = oblist1.add(new EducationViewTable(
                        resultSet.getString("fld_FirstName"),
                        resultSet.getString("fld_UserName"),
                        resultSet.getString("fld_Address"),
                        resultSet.getString("fld_TelephoneNumber"),
                        resultSet.getString("fld_UserType"),
                        resultSet.getString("fld_Password")));

                mainPlaylists.add(resultSet.getString("fld_FirstName"));
                System.out.println(mainPlaylists);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        col_name.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("TelephoneNumber"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("UserType"));

        tableUserView.setItems(oblist1);
    }


}