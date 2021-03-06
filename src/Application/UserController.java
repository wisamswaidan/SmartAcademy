package Application;

import Domain.UserConstructor;
import Foundation.DB;
import Technical.JDBC;
import Technical.StoredProcedures;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.*;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


public class UserController implements Initializable{

    //Start the connection to DB.
    Connection con = DB.connect();
    //Create preparedStatement Object
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Button addBut , selectBut, editBut ,removeBut , backBut;

    //Create tableView and TableColumns for tables
    @FXML
    private TableView<UserConstructor> tableUserView;
    @FXML
    private TableColumn<UserConstructor, String> col_firstname;
    @FXML
    private TableColumn<UserConstructor, String> col_lastname;
    @FXML
    private TableColumn<UserConstructor, String> col_phone;
    @FXML
    private TableColumn<UserConstructor, String> col_address;
    @FXML
    private TableColumn<UserConstructor, String> col_zipcode;
    @FXML
    private TableColumn<UserConstructor, String> col_username;
    @FXML
    private TableColumn<UserConstructor, String> col_password;
    @FXML
    private TableColumn<UserConstructor, String> col_usertype;


    //FXML for TestFields
    @FXML
    private TextField phone_TextField,address_TextField,zipcode_TextField,username_TextField,lastname_TextField,password_TextField,firstname_TextField ;
    @FXML
    private ChoiceBox<String> accessBox;

    //Create ObservableList to read user access level from database and use it for Drop menu
    ObservableList<String> accessStatus = FXCollections.observableArrayList();
    //Create ObservableList to read data from database and add it to the list to control it (Select , Delete and Edit)
    ObservableList<UserConstructor> oblistUser = FXCollections.observableArrayList();
    List<String> matchFoundList = new ArrayList<String>();


    /**
     * View Users in TableView from the database.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DB.selectSQL("SELECT fld_UserType from tbl_UsersType");
        do
        {
            String dataUserType = DB.getDisplayData();
            if (dataUserType.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                accessStatus.add(dataUserType.trim());
                accessBox.setItems(accessStatus);

            }
        } while (true);

        //JDBC to view the users .
        JDBC viewUserList = new JDBC();
        viewUserList.ViewUserTSQL();

        try {
            preparedStatement = con.prepareStatement(viewUserList.ViewUserTSQL());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                oblistUser.add(new UserConstructor(
                        resultSet.getString("fld_firstname"),
                        resultSet.getString("fld_lastname"),
                        resultSet.getString("fld_telephonenumber"),
                        resultSet.getString("fld_address"),
                        resultSet.getString("fld_zipcode"),
                        resultSet.getString("fld_username"),
                        resultSet.getString("fld_password"),
                        resultSet.getString("fld_usertype")));



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Set the result in TableView.
        col_firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("TelephoneNumber"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        col_zipcode.setCellValueFactory(new PropertyValueFactory<>("Zipcode"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("UserType"));
        tableUserView.setItems(oblistUser);
    }

    /**
     * Remove User .
     */
    @FXML
    public void removeUser(){

        //JDBC
        JDBC deleteUser = new JDBC();
        deleteUser.DeleteUserTSQL();

        //Create an object for selection cells...
        TablePosition pos = tableUserView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        UserConstructor item = tableUserView.getItems().get(row);
        String getUserNameField = item.getUserName();
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();

        try {
            preparedStatement = con.prepareStatement(deleteUser.DeleteUserTSQL());
            preparedStatement.setString(1,getUserNameField);
            //We use executeUpdate() instead of executeQuery() because we don't expect any return .
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Delete Done" );

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Select User To Edit.
     */

    @FXML
    public void selectToEditUser(){

        //Create an object for selection cells...
        TablePosition pos = tableUserView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the educations_table view type:
        UserConstructor item = tableUserView.getItems().get(row);
        //Get the username from the selecting table.
        String getUserNameField = item.getUserName();
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();

        try {

            //Start the StoredProcedures
            StoredProcedures.ViewSpecificUser();
            ResultSet rs;
            try (CallableStatement stmt = con.prepareCall(StoredProcedures.ViewSpecificUser())) {
                //pass all the parameters to the stored procedure.
                stmt.setString(1, getUserNameField);
                rs = stmt.executeQuery();
                //Get the results
                while (rs.next()) {

                    String fld_FirstName = rs.getString("fld_FirstName");
                    firstname_TextField.setText(fld_FirstName);

                    String fld_LastName = rs.getString("fld_LastName");
                    lastname_TextField.setText(fld_LastName);

                    String fld_TelephoneNumber = rs.getString("fld_TelephoneNumber");
                    phone_TextField.setText(fld_TelephoneNumber);

                    String fld_Address = rs.getString("fld_Address");
                    address_TextField.setText(fld_Address);

                    String fld_Zipcode = rs.getString("fld_Zipcode");
                    zipcode_TextField.setText(fld_Zipcode);

                    String fld_UserName = rs.getString("fld_UserName");
                    username_TextField.setText(fld_UserName);
                    username_TextField.setDisable(true);

                    String fld_Password = rs.getString("fld_Password");
                    password_TextField.setText(fld_Password);

                    String fld_UserType = rs.getString("fld_UserType");

                    //Set the user type inside the Drop menu
                    accessBox.setValue(fld_UserType.trim());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Edit a User .
     */
    @FXML
    public void editUser(){

        //Create an object from JBDC class
        JDBC update_User = new JDBC();
        update_User.UpdateUserTSQL();

        // Get the choosen data from Drop Menu
        String UserAccessLevel = accessBox.getValue();

        String firstname = firstname_TextField.getText().trim();
        String lastname = lastname_TextField.getText().trim();
        String phone = phone_TextField.getText().trim();
        String address = address_TextField.getText().trim();
        String zipcode = zipcode_TextField.getText().trim();
        String username = username_TextField.getText().trim();
        String password = password_TextField.getText().trim();



        try {
            //Start the JDBC
            preparedStatement = con.prepareStatement(update_User.UpdateUserTSQL());

            preparedStatement.setString(1,firstname);
            preparedStatement.setString(2,lastname);
            preparedStatement.setInt(3,Integer.parseInt(phone));
            preparedStatement.setString(4,address);
            preparedStatement.setInt(5,Integer.parseInt(zipcode));
            preparedStatement.setString(6,password);
            preparedStatement.setString(7,UserAccessLevel);
            preparedStatement.setString(8,username);

            //System.out.println(UserAccessLevel);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Edit Done" );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Wrong with Editing " );
            e.printStackTrace();
        }
    }

    /**
     * Create a new  User .
     */
    @FXML
    public void addUser(ActionEvent event) throws Exception {

        //Create an object from JBDC class
        JDBC createUser = new JDBC();
        createUser.CreateUserTSQL();

        String UserAccessLevel = accessBox.getValue();

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

            if (un.equals(username.toLowerCase())){
                JOptionPane.showMessageDialog(null, "Username already exist");
            }
            else{
                try {
                    //Start the JDBC
                    preparedStatement = con.prepareStatement(createUser.CreateUserTSQL());
                    preparedStatement.setString(1,firstname);
                    preparedStatement.setString(2,lastname);
                    preparedStatement.setInt(3, Integer.parseInt(phone));
                    preparedStatement.setString(4, address);
                    preparedStatement.setInt(5, Integer.parseInt(zipcode));
                    preparedStatement.setString(6,username);
                    preparedStatement.setString(7,password);
                    preparedStatement.setString(8,UserAccessLevel);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Create Done" );

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Wrong with creating" );
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Back to the main scene.
     */
    public void backButton(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/main.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }
}
