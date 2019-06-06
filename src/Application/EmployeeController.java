package Application;

import Domain.EmployeeConstructor;
import Foundation.DB;
import Technical.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    //Start the connection to DB.
    Connection con = DB.connect();
    //Create preparedStatement Object
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Button addBut , selectBut, editBut ,removeBut ,backBut;

    //Create tableView and TableColumns for tables
    @FXML
    private TableView<EmployeeConstructor> tableEmpView;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_firstname;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_lastname;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_email;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_mobile;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_address;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_zipcode;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_information;
    @FXML
    private TableColumn<EmployeeConstructor, String> col_companyID;



    //FXML for TestFields
    @FXML
    private TextField firstname_TextField,lastname_TextField,email_TextField,mobile_TextField,address_TextField,zipcode_TextField,information_TextField ;
    @FXML
    private ChoiceBox<String> accessBox;

    //Create ObservableList to read user access level from database and use it for Drop menu
    ObservableList<String> companyID = FXCollections.observableArrayList();
    //Create ObservableList to read data from database and add it to the list to control it (Select , Delete and Edit)
    ObservableList<EmployeeConstructor> oblistEmp = FXCollections.observableArrayList();
    List<String> matchFoundList = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //JDBC
        JDBC viewEmpList = new JDBC();
        viewEmpList.ViewEmployeeTSQL();

        DB.selectSQL("SELECT fld_CompanyID from tbl_Companies");
        do
        {
            String dataUserType = DB.getDisplayData();
            if (dataUserType.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                companyID.add(dataUserType.trim());
                accessBox.setItems(companyID);

            }
        } while (true);

        try {

            preparedStatement = con.prepareStatement(viewEmpList.ViewEmployeeTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                oblistEmp.add(new EmployeeConstructor(
                        resultSet.getString("fld_FirstName"),
                        resultSet.getString("fld_LastName"),
                        resultSet.getString("fld_Email"),
                        resultSet.getString("fld_Mobile"),
                        resultSet.getString("fld_Address"),
                        resultSet.getString("fld_Zipcode"),
                        resultSet.getString("fld_Information"),
                        resultSet.getString("fld_CompanyID")));
                        // Add the value we need to check for a match with to the list
                        matchFoundList.add(resultSet.getString("fld_Mobile"));
            }

        } catch (SQLException e) {
            //e.printStackTrace();
        }


        col_firstname.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col_mobile.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        col_zipcode.setCellValueFactory(new PropertyValueFactory<>("Zipcode"));
        col_information.setCellValueFactory(new PropertyValueFactory<>("Information"));
        col_companyID.setCellValueFactory(new PropertyValueFactory<>("CompanyID"));

        //View the list in TableView
        tableEmpView.setItems(oblistEmp);
    }

    @FXML
    public void removeEmp(){

        //JBDC
        JDBC deleteEmp = new JDBC();
        deleteEmp.DeleteEmployeeTSQL();

        //Create an object for selection cells...
        TablePosition pos = tableEmpView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        EmployeeConstructor item = tableEmpView.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        System.out.println(data);

        //Check Method if the user select the correct column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0; i < matchFoundList.size() ; i++){

            String userSearchMatch = matchFoundList.get(i);
            if (data.equals(userSearchMatch)){
                System.out.println("match found " + userSearchMatch);
                resultMatch = userSearchMatch;
                matchBoolena = true;

                if(matchBoolena == true){
                    try {
                        preparedStatement = con.prepareStatement(deleteEmp.DeleteEmployeeTSQL());
                        preparedStatement.setString(1,resultMatch);

                        //We use executeUpdate() instead of executeQuery() because we don't expect any return .
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Delete Done" );

                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Please check if the employee have a education plane first" );
                    }
                }
            }
        }

        if(matchBoolena == false){
            JOptionPane.showMessageDialog(null, "Please choose Mobile column " );
        }

    }

    //Method to select the company wants to edit
    @FXML
    public void selectToEditEmp(){

        //Create an object from JBDC class
        JDBC editEmp = new JDBC();
        editEmp.EditEmployeeTSQL();

        //Create an object for selection cells...
        TablePosition pos = tableEmpView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the educations_table view type:
        EmployeeConstructor item = tableEmpView.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        //System.out.println(data);


        //Check Method if the user select the AMU column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0; i < matchFoundList.size() ; i++){

            String userSearchMatch = matchFoundList.get(i);
            if (data.equals(userSearchMatch)){
                resultMatch = userSearchMatch;
                //System.out.println(resultMatch);

                matchBoolena = true;

                if(matchBoolena == true){

                    try {
                        //Start the JDBC
                        preparedStatement = con.prepareStatement(editEmp.EditEmployeeTSQL());
                        preparedStatement.setString(1,resultMatch);
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {
                            String fld_FirstName = rs.getString("fld_FirstName");
                            firstname_TextField.setText(fld_FirstName);

                            String fld_LastName = rs.getString("fld_LastName");
                            lastname_TextField.setText(fld_LastName);

                            String fld_Email = rs.getString("fld_Email");
                            email_TextField.setText(fld_Email);

                            String fld_Mobile = rs.getString("fld_Mobile");
                            mobile_TextField.setText(fld_Mobile);
                            mobile_TextField.setDisable(true);

                            String fld_Address = rs.getString("fld_Address");
                            address_TextField.setText(fld_Address);

                            String fld_Zipcode = rs.getString("fld_Zipcode");
                            zipcode_TextField.setText(fld_Zipcode);


                            String fld_Information = rs.getString("fld_Information");
                            information_TextField.setText(fld_Information);

                            String fld_CompanyID = rs.getString("fld_CompanyID");

                            //Set the the user type inside the Drop menu
                            accessBox.setValue(fld_CompanyID.trim());

                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        if(matchBoolena == false){
            JOptionPane.showMessageDialog(null, "Please choose Mobile Number " );
        }


    }

    //Method to Edit any company
    @FXML
    public void editEmp(){

        //Create an object from JBDC class
        JDBC updateEmp = new JDBC();
        updateEmp.UpdateEmpTSQL();

        //Get the Company ID value from Drop menu
        String companyID = accessBox.getValue();

        //String companyID = accessBox.getValue();
        String firstname = firstname_TextField.getText().trim();
        String lastname = lastname_TextField.getText().trim();
        String email = email_TextField.getText().trim();
        String mobile = mobile_TextField.getText().trim();
        String address = address_TextField.getText().trim();
        String zipcode = zipcode_TextField.getText().trim();
        String information = information_TextField.getText().trim();

        try {
            //Start the JDBC
            preparedStatement = con.prepareStatement(updateEmp.UpdateEmpTSQL());
            preparedStatement.setString(1,firstname);
            preparedStatement.setString(2,lastname);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,address);
            preparedStatement.setInt(5,Integer.parseInt(zipcode));
            preparedStatement.setString(6,information);
            preparedStatement.setString(7,companyID);
            preparedStatement.setInt(8,Integer.parseInt(mobile));

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Edit Done" );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Wrong with Editing " );
            e.printStackTrace();
        }

    }

    @FXML
    public void addEmp(ActionEvent event) throws Exception {

        //Create an object from JBDC class
        JDBC createEmp = new JDBC();
        createEmp.CreateEmployeeTSQL();

        // Get the chosen data from Drop Menu
        String companyID = accessBox.getValue();

        String firstname = firstname_TextField.getText().trim();
        String lastname = lastname_TextField.getText().trim();
        String email = email_TextField.getText().trim();
        String mobile = mobile_TextField.getText().trim();
        String address = address_TextField.getText().trim();
        String zipcode = zipcode_TextField.getText().trim();
        String information = information_TextField.getText().trim();


        if (firstname.equals("") || lastname.equals("") || email.equals("") || mobile.equals("") || address.equals("") || zipcode.equals("") || information.equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all fields, some are empty");

        } else {
            DB.selectSQL("SELECT fld_Mobile from tbl_CompanyEmployees where fld_Mobile = '" + mobile + "'");
            String un = DB.getData();

            if (un.equals(mobile.toLowerCase())){
                JOptionPane.showMessageDialog(null, "Mobile already exist");
            }
            else{
                try {
                    //Start the JDBC
                    preparedStatement = con.prepareStatement(createEmp.CreateEmployeeTSQL());
                    preparedStatement.setString(1,firstname);
                    preparedStatement.setString(2,lastname);
                    preparedStatement.setString(3,email);
                    preparedStatement.setInt(4, Integer.parseInt(mobile));
                    preparedStatement.setString(5,address);
                    preparedStatement.setInt(6,Integer.parseInt(zipcode));
                    preparedStatement.setString(7,information);
                    preparedStatement.setString(8,companyID);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Create Done" );

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Wrong with creating" );
                    e.printStackTrace();
                }
            }
        }
    }


    //Method to back to the main scene
    public void backButton(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/main.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }

}

