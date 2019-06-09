package Application;

import Domain.CompanyConstructor;
import Foundation.DB;
import Technical.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanyController implements  Initializable{

    //Start connection to the Database.
    Connection con = DB.connect();
    //Create preparedStatement Object
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    //Initialize new Buttons.
    @FXML
    private Button selectButton , editButton , deleteButton , createButton , backBut;

    //Initialize tableView and TableColumns
    @FXML
    private TableView <CompanyConstructor> companies_table;
    @FXML
    private TableColumn <CompanyConstructor, String> col_id;
    @FXML
    private TableColumn <CompanyConstructor, String> col_name;
    @FXML
    private TableColumn <CompanyConstructor, String> col_cvr;
    @FXML
    private TableColumn <CompanyConstructor, String> col_TeleNumber;
    @FXML
    private TableColumn <CompanyConstructor, String> col_address;
    @FXML
    private TableColumn <CompanyConstructor, String> col_zipcode;
    @FXML
    private TableColumn <CompanyConstructor, String> col_employeNum;
    @FXML
    private TableColumn <CompanyConstructor, String> col_information;

    //FXML for TestFields
    @FXML
    private TextField companyID_TextField,name_TextField,cvr_TextField, tele_TextField,address_TextField ,information_TextField, zipcode_TextField,numEmployees_TextField ;

    //Create ObservableList to read data from database and add it to the list to control it (Select , Delete and Edit)
    ObservableList<CompanyConstructor> oblist = FXCollections.observableArrayList();
    List<String> matchFoundList = new ArrayList<String>();

    /**
     * View companies data in TableView from the database.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JDBC viewCompanies = new JDBC();
        viewCompanies.ViewCompanyTSQL();

        try {
            preparedStatement = con.prepareStatement(viewCompanies.ViewCompanyTSQL());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                oblist.add(new CompanyConstructor(
                        resultSet.getString("fld_CompanyID"),
                        resultSet.getString("fld_Name"),
                        resultSet.getString("fld_CVR"),
                        resultSet.getString("fld_TelephoneNumber"),
                        resultSet.getString("fld_Address"),
                        resultSet.getString("fld_Zipcode"),
                        resultSet.getString("fld_NumberOfEmployees"),
                        resultSet.getString("fld_Information")));
                        // Add the value we need to check for a match with to the list
                        matchFoundList.add(resultSet.getString("fld_CompanyID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //Set the result in TableView
        col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_cvr.setCellValueFactory(new PropertyValueFactory<>("CVR"));
        col_TeleNumber.setCellValueFactory(new PropertyValueFactory<>("TeleNumber"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        col_zipcode.setCellValueFactory(new PropertyValueFactory<>("Zipcode"));
        col_employeNum.setCellValueFactory(new PropertyValueFactory<>("NumOfEmplyees"));
        col_information.setCellValueFactory(new PropertyValueFactory<>("Information"));
        companies_table.setItems(oblist);
    }

    /**
     * Remove a company.
     */
    @FXML
    public void removeComp(){

        // JDBC to delete a company .
        JDBC deleteComp = new JDBC();
        deleteComp.DeleteCompanyTSQL();

        //Create an object for selection cells...
        TablePosition pos = companies_table.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the educations_table view type:
        CompanyConstructor item = companies_table.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();

        //Check Method if the user select the AMU column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0; i < matchFoundList.size() ; i++){

            String amuSearchMatch = matchFoundList.get(i);
            if (data.equals(amuSearchMatch)){
                //System.out.println("match found " + amuSearchMatch);
                resultMatch = amuSearchMatch;
                matchBoolena = true;

                if(matchBoolena == true){
                    try {
                        preparedStatement = con.prepareStatement(deleteComp.DeleteCompanyTSQL());
                        preparedStatement.setString(1,resultMatch);
                        //We use executeUpdate() instead of executeQuery() because we dont expect any return .
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Delete Done" );

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(matchBoolena == false){
            JOptionPane.showMessageDialog(null, "Please choose AMU column " ); }
    }

    /**
     * select the company wants to edit.
     */
    @FXML
    public void selectToEditComp(){

        // JDBC to edit a company .
        JDBC edit_Comp = new JDBC();
        edit_Comp.EditCompanyTSQL();

        //Create an object for selection cells...
        TablePosition pos = companies_table.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the educations_table view type:
        CompanyConstructor item = companies_table.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();

        //Check Method if the user select the AMU column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0; i < matchFoundList.size() ; i++){

            String amuSearchMatch = matchFoundList.get(i);
            if (data.equals(amuSearchMatch)){

                resultMatch = amuSearchMatch;
                System.out.println(resultMatch);

                matchBoolena = true;

                if(matchBoolena == true){

                    try {
                        //Start the JDBC
                        preparedStatement = con.prepareStatement(edit_Comp.EditCompanyTSQL());
                        preparedStatement.setInt(1,Integer.parseInt(resultMatch));
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {
                            String table_id = rs.getString("fld_CompanyID");
                            companyID_TextField.setText(table_id);
                            companyID_TextField.setDisable(true);

                            String table_name = rs.getString("fld_Name");
                            name_TextField.setText(table_name);

                            String table_cvr = rs.getString("fld_CVR");
                            cvr_TextField.setText(table_cvr);

                            String table_tele = rs.getString("fld_TelephoneNumber");
                            tele_TextField.setText(table_tele);

                            String table_address = rs.getString("fld_Address");
                            address_TextField.setText(table_address);

                            String table_zipcode = rs.getString("fld_Zipcode");
                            zipcode_TextField.setText(table_zipcode);

                            String table_NumberOfEmployees = rs.getString("fld_NumberOfEmployees");
                            numEmployees_TextField.setText(table_NumberOfEmployees);

                            String table_info = rs.getString("fld_Information");
                            information_TextField.setText(table_info);

                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        if(matchBoolena == false){
            JOptionPane.showMessageDialog(null, "Please choose ID column " );
        }
    }

    /**
     * Edit a company.
     */
    @FXML
    public void editComp(){

        // JDBC to edit a company .
        JDBC update_Comp = new JDBC();
        update_Comp.UpdateCompanyTSQL();

        //Text field read the javafx fields .
        String id = companyID_TextField.getText().trim();
        String name = name_TextField.getText().trim();
        String cvr = cvr_TextField.getText().trim();
        String tele_number = tele_TextField.getText().trim();
        String address = address_TextField.getText().trim();
        String zipcode = zipcode_TextField.getText().trim();
        String numEmployees = numEmployees_TextField.getText().trim();
        String information = information_TextField.getText().trim();

        try {
            //Start the JDBC
            preparedStatement = con.prepareStatement(update_Comp.UpdateCompanyTSQL());
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,Integer.parseInt(cvr));
            preparedStatement.setInt(3,Integer.parseInt(tele_number));
            preparedStatement.setString(4,address);
            preparedStatement.setInt(5,Integer.parseInt(zipcode));
            preparedStatement.setInt(6,Integer.parseInt(numEmployees));
            preparedStatement.setString(7,information);
            preparedStatement.setInt(8,Integer.parseInt(id));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Edit Done" );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Wrong with Editing " );
            e.printStackTrace();
        }
    }

    /**
     * Create a new company.
     */
    @FXML
    public void addComp(){

        // JDBC to create new a company .
        JDBC createComp = new JDBC();
        createComp.CreateCompanyTSQL();

        //Text field read the javafx fields .
        String id = companyID_TextField.getText().trim();
        String name = name_TextField.getText().trim();
        String cvr = cvr_TextField.getText().trim();
        String tele_number = tele_TextField.getText().trim();
        String address = address_TextField.getText().trim();
        String zipcode = zipcode_TextField.getText().trim();
        String numEmployees = numEmployees_TextField.getText().trim();
        String information = information_TextField.getText().trim();

        //Check if all the fields are not empty
        if (id.equals("") || name.equals("") || cvr.equals("")  || tele_number.equals("") || address.equals("") || zipcode.equals("") || numEmployees.equals("") || information.equals("") ) {
            JOptionPane.showMessageDialog(null, "Please fill all fields, some are empty");

        } else {
            DB.selectSQL("SELECT fld_CompanyID from tbl_Companies where fld_CompanyID = '" + id + "'");
            String un = DB.getData();

            if (un.equals(id.toLowerCase())){
                JOptionPane.showMessageDialog(null, "ID already exist");
            }
            else{
                try {
                    //Start the JDBC
                    preparedStatement = con.prepareStatement(createComp.CreateCompanyTSQL());
                    preparedStatement.setInt(1,Integer.parseInt(id));
                    preparedStatement.setString(2,name);
                    preparedStatement.setInt(3,Integer.parseInt(cvr));
                    preparedStatement.setInt(4,Integer.parseInt(tele_number));
                    preparedStatement.setString(5,address);
                    preparedStatement.setInt(6,Integer.parseInt(zipcode));
                    preparedStatement.setInt(7,Integer.parseInt(numEmployees));
                    preparedStatement.setString(8,information);
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
     * Back to the main scene .
     */
    public void backButton(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/main.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }

    /**
     * Export to Excel file .
     */
    public void exportExcel(){

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Companies");
        XSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("fld_CompanyID");
        header.createCell(1).setCellValue("fld_Name");
        header.createCell(2).setCellValue("fld_CVR");
        header.createCell(3).setCellValue("fld_TelephoneNumber");
        header.createCell(4).setCellValue("fld_Address");
        header.createCell(5).setCellValue("fld_Zipcode");
        header.createCell(6).setCellValue("fld_NumberOfEmployees");
        header.createCell(7).setCellValue("fld_Information");

        //Set the size to be auto.
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        //Set the zoom to 125%
        sheet.setZoom(125);

        int index = 1;

        //JDBC to view the companies
        JDBC viewCompanies = new JDBC();
        viewCompanies.ViewCompanyTSQL();

        try {
            preparedStatement = con.prepareStatement(viewCompanies.ViewCompanyTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(resultSet.getString("fld_CompanyID"));
                row.createCell(1).setCellValue(resultSet.getString("fld_Name"));
                row.createCell(2).setCellValue(resultSet.getString("fld_CVR"));
                row.createCell(3).setCellValue(resultSet.getString("fld_TelephoneNumber"));
                row.createCell(4).setCellValue(resultSet.getString("fld_Address"));
                row.createCell(5).setCellValue(resultSet.getString("fld_Zipcode"));
                row.createCell(6).setCellValue(resultSet.getString("fld_NumberOfEmployees"));
                row.createCell(7).setCellValue(resultSet.getString("fld_Information"));
                index++;
            }

            FileOutputStream fileout = new FileOutputStream("Companies.xlsx");
            wb.write(fileout);
            fileout.close();
            JOptionPane.showMessageDialog(null, "Export Done" );

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
