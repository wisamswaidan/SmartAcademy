package Application;
import Domain.EducationViewTable;
import Foundation.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Create_User {

    Connection con = DB.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

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

    @FXML
    private void initialize(){

        ObservableList<String> accessStatus = FXCollections.observableArrayList();


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