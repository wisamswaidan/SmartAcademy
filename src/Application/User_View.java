package Application;

import Domain.EducationViewTable;
import Domain.UserViewTable;
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
    private TableView<UserViewTable> tableUserView;

    @FXML
    private TableColumn<UserViewTable, String> col_firstname;

    @FXML
    private TableColumn<UserViewTable, String> col_lastname;

    @FXML
    private TableColumn<UserViewTable, String> col_phone;

    @FXML
    private TableColumn<UserViewTable, String> col_address;

    @FXML
    private TableColumn<UserViewTable, String> col_zipcode;

    @FXML
    private TableColumn<UserViewTable, String> col_username;

    @FXML
    private TableColumn<UserViewTable, String> col_password;

    @FXML
    private TableColumn<UserViewTable, String> col_usertype;



    ObservableList<UserViewTable> oblist1 = FXCollections.observableArrayList();
    List<String> mainPlaylists = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JDBC viewUserList = new JDBC();
        viewUserList.ViewUserTSQL();

        try {

            preparedStatement = con.prepareStatement(viewUserList.ViewUserTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                boolean add = oblist1.add(new UserViewTable(
                        resultSet.getString("fld_firstname"),
                        resultSet.getString("fld_lastname"),
                        resultSet.getString("fld_telephonenumber"),
                        resultSet.getString("fld_address"),
                        resultSet.getString("fld_zipcode"),
                        resultSet.getString("fld_username"),
                        resultSet.getString("fld_password"),
                        resultSet.getString("fld_usertype")));

                mainPlaylists.add(resultSet.getString("fld_firstname"));
                System.out.println(mainPlaylists);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        col_firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("TelephoneNumber"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        col_zipcode.setCellValueFactory(new PropertyValueFactory<>("Zipcode"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("UserType"));

        tableUserView.setItems(oblist1);
    }


}