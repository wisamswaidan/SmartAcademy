package Application;

import Domain.UserViewTable;
import Foundation.DB;
import Technical.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.*;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;

import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.swing.*;

import javafx.event.ActionEvent;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UserController implements Initializable{

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

    @FXML
    private TextField phone_TextField;

    @FXML
    private TextField address_TextField;

    @FXML
    private TextField zipcode_TextField;

    @FXML
    private TextField username_TextField;

    @FXML
    private ChoiceBox<String> accessBox;

    @FXML
    private TextField lastname_TextField;

    @FXML
    private TextField password_TextField;

    @FXML
    private TextField firstname_TextField;



    ObservableList<UserViewTable> oblist1 = FXCollections.observableArrayList();
    ObservableList<String> accessStatus = FXCollections.observableArrayList();
    List<String> mainPlaylists = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DB.selectSQL("SELECT fld_UserType from tbl_UsersType");
        do
        {
            String data = DB.getDisplayData();
            if (data.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                accessStatus.add(data.trim());
                accessBox.setValue("Admin");
                accessBox.setItems(accessStatus);
            }
        } while (true);

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
                        mainPlaylists.add(resultSet.getString("fld_username"));
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

    @FXML
    public void removeUser(){
        //Create an object for selection cells...
        TablePosition pos = tableUserView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        UserViewTable item = tableUserView.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        System.out.println(data);

//        TableView.TableViewSelectionModel selectionModel = tableUserView.getSelectionModel();
//        ObservableList selectedCells = selectionModel.getSelectedCells();
//        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
//        int newValue = 0;
//        Object val = tablePosition.getTableColumn().getCellData(newValue);
//        System.out.println("Selected Value" + val);


        //JBDC
        JDBC deleteUser = new JDBC();
        deleteUser.DeleteUserTSQL();

        //Check Method if the user select the correct column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0 ; i < mainPlaylists.size() ; i++){

            String userSearchMatch = mainPlaylists.get(i);
            if (data.equals(userSearchMatch)){
                System.out.println("match found " + userSearchMatch);
                resultMatch = userSearchMatch;
                matchBoolena = true;

                if(matchBoolena == true){
                    try {
                        preparedStatement = con.prepareStatement(deleteUser.DeleteUserTSQL());
                        preparedStatement.setString(1,resultMatch);
                        //We use executeUpdate() instead of executeQuery() because we don't expect any return .
                        preparedStatement.executeUpdate();
                        System.out.println("Delete Done for " + userSearchMatch);
                        JOptionPane.showMessageDialog(null, "Delete Done" );

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(matchBoolena == false){
            JOptionPane.showMessageDialog(null, "Please choose UserName column " );
        }

    }

    @FXML
    public void addUser(ActionEvent event) throws Exception {

        String firstname = firstname_TextField.getText().trim();
        String lastname = lastname_TextField.getText().trim();
        String phone = phone_TextField.getText().trim();
        String address = address_TextField.getText().trim();
        String zipcode = zipcode_TextField.getText().trim();
        String username = username_TextField.getText().trim();
        String password = password_TextField.getText().trim();


        if (firstname.equals("") || lastname.equals("") || phone.equals("") || address.equals("") || zipcode.equals("") || username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all fields, some are empty");

        } else {
            DB.selectSQL("SELECT fld_UserName from tbl_Users where fld_UserName = '" + username + "'");
            String un = DB.getData();

            do{
                if (un.equals(username.toLowerCase())){
                    JOptionPane.showMessageDialog(null, "Username already exist");
                    break;
                }else{
                    System.out.println("Username does not exist");
                    DB.insertSQL("INSERT INTO tbl_Users VALUES('" + firstname + "', '" + lastname + "', '" + phone + "' , '" + address + "', '" + zipcode + "' , '" + username + "', '" + password + "', 'interviewer')");

//                    USE EASVSMART;
//                    INSERT INTO tbl_Users(fld_FirstName, fld_LastName, fld_TelephoneNumber, fld_Address, fld_Zipcode, fld_UserName, fld_Password, fld_UserType)
//                    VALUES ('Test', 'Test 2', '12345678' , 'Søgræsvej 8', '6400' , 'admin', 'test', 'admin' )
                    //here i need to add the username to the database with the remaining fields - before get access level from database 2. we need to add validation to each field to be as in database
                    break;
                }

            }while (true);
        }

    }
}