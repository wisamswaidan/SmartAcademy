package Application;

import Domain.AssignUserContractor;
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

public class AssignController implements Initializable {

    //Start the connection to DB.
    Connection con = DB.connect();
    //Create preparedStatement Object
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    private Button assignBut , deleteBut , backBut;
    @FXML
    private ChoiceBox<String> companyBox;
    @FXML
    private ChoiceBox<String> userBox;
    @FXML
    private TableView<AssignUserContractor> assignTable;
    @FXML
    private TableColumn<AssignUserContractor, String> col_Company;
    @FXML
    private TableColumn<AssignUserContractor, String> col_User;

    //Create ObservableList to read user access level from database and use it for Drop menu
    ObservableList<String> companyIDList = FXCollections.observableArrayList();
    ObservableList<String> userIDList = FXCollections.observableArrayList();
    ObservableList<AssignUserContractor> oblist = FXCollections.observableArrayList();

    List<String> companyIDSearch = new ArrayList<String>();
    List<String> usernameSearch = new ArrayList<String>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DB.selectSQL("SELECT fld_CompanyID from tbl_Companies");
        do
        {
            String companyID = DB.getDisplayData();
            if (companyID.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                companyIDList.add(companyID.trim());
                companyBox.setItems(companyIDList);

            }
        } while (true);

        //
        DB.selectSQL("SELECT fld_UserName from tbl_Users");
        do
        {
            String userID = DB.getDisplayData();
            if (userID.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                userIDList.add(userID.trim());
                userBox.setItems(userIDList);

            }
        } while (true);



        //JDBC
        JDBC viewAssignedList = new JDBC();
        viewAssignedList.ViewAssignTSQL();

        try {

            preparedStatement = con.prepareStatement(viewAssignedList.ViewAssignTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                oblist.add(new AssignUserContractor(
                        resultSet.getString("fld_ComapnyID"),
                        resultSet.getString("fld_UserName")));
                        // Add the value we need to check for a match with to the list
                        companyIDSearch.add(resultSet.getString("fld_ComapnyID"));
                        usernameSearch.add(resultSet.getString("fld_UserName"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_Company.setCellValueFactory(new PropertyValueFactory<>("Company"));
        col_User.setCellValueFactory(new PropertyValueFactory<>("User"));
        assignTable.setItems(oblist);


    }

    @FXML
    public void assignUserToCom() throws SQLException {

        // Get the chosen data from Drop Menu
        String getCompanyID = companyBox.getValue();
        String getUserList = userBox.getValue();

        //Create an object from JBDC class
        JDBC createAssign = new JDBC();
        createAssign.CreateAssignTSQL();

        try {
            preparedStatement = con.prepareStatement(createAssign.CreateAssignTSQL());
            preparedStatement.setInt(1, Integer.parseInt(getCompanyID));
            preparedStatement.setString(2, getUserList);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Assign Done" );

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void removeAssigned(){

        //JBDC
        JDBC deleteAssigned = new JDBC();
        deleteAssigned.DeleteAssignTSQL();

        //Create an object for selection cells...
        TablePosition pos = assignTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        AssignUserContractor item = assignTable.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();

        //Check Method if the user select the correct column to start the Delete process .
        boolean matchBoolena = false;
        String matchFoundCompany = "";
        String matchFoundUser = "";

        for(int i = 0; i < companyIDSearch.size() ; i++){

            String searchCom = companyIDSearch.get(i);
            if (data.equals(searchCom)){
                System.out.println(companyIDSearch.get(i) + " " + usernameSearch.get(i));

                matchFoundCompany = companyIDSearch.get(i);
                matchFoundUser = usernameSearch.get(i);
                matchBoolena = true;
            }
        }
        //Start the Delete JDBC
        if(matchBoolena == true){
            try {
                preparedStatement = con.prepareStatement(deleteAssigned.DeleteAssignTSQL());
                preparedStatement.setString(1,matchFoundCompany);
                preparedStatement.setString(2,matchFoundUser);

                //We use executeUpdate() instead of executeQuery() because we don't expect any return .
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Delete Done" );

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error" );
                e.printStackTrace();
            }
        }

        else{
            System.out.println("Error");
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
