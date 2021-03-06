package Application;

import Domain.EducationPlanConstractor;
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

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EducationPlanController implements Initializable {

    //PreparedStatement
    Connection con = DB.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    //Initialize new Buttons.
    @FXML
    private Button addBut  ,removeBut ,backBut , exportBut;

    //Initialize tableView and TableColumns for tables
    @FXML
    private TableView<EducationPlanConstractor> tableEduPlan;
    @FXML
    private TableColumn<EducationPlanConstractor, String> col_PlanID;
    @FXML
    private TableColumn<EducationPlanConstractor, String> col_EducationAMU;
    @FXML
    private TableColumn<EducationPlanConstractor, String> col_EmployeeMobile;
    @FXML
    private TableColumn<EducationPlanConstractor, String> col_CompanyID;
    @FXML
    private TableColumn<EducationPlanConstractor, String> col_Information;
    @FXML
    private TableColumn<EducationPlanConstractor, String> col_EduSch_ID;

    //Initialize ChoiceBox.
    @FXML
    private ChoiceBox<String> eduAMUaccessBox;
    @FXML
    private ChoiceBox<String> empMobileAccessBox;
    @FXML
    private ChoiceBox<String> companyIDaccessBox;
    @FXML
    private ChoiceBox<String> eduSchaccessBox;


    //FXML for TestFields
    @FXML
    private TextField planID_TextField ,information_TextField;

    //Create ObservableList to read user access level from database and use it for Drop menu
    ObservableList<String> eduAMU = FXCollections.observableArrayList();
    ObservableList<String> empMobile = FXCollections.observableArrayList();
    ObservableList<String> companyID = FXCollections.observableArrayList();
    ObservableList<String> eduSch = FXCollections.observableArrayList();

    //Create ObservableList to read data from database and add it to the list to control it (Select , Delete and Edit)
    ObservableList<EducationPlanConstractor> oblist = FXCollections.observableArrayList();
    List<String> matchFoundList = new ArrayList<String>();

    /**
     * View Educations Plan data in TableView from the database.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //AMU ChoiceBox
        DB.selectSQL("SELECT fld_AMU from tbl_Educations");
        do
        {
            String dataUserType = DB.getDisplayData();
            if (dataUserType.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                eduAMU.add(dataUserType.trim());
                eduAMUaccessBox.setItems(eduAMU);

            }
        } while (true);

        //EmployeeMobile ChoiceBox
        DB.selectSQL("SELECT fld_Mobile from tbl_CompanyEmployees");
        do
        {
            String dataUserType = DB.getDisplayData();
            if (dataUserType.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                empMobile.add(dataUserType.trim());
                empMobileAccessBox.setItems(empMobile);

            }
        } while (true);

        //companyID ChoiceBox
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
                companyIDaccessBox.setItems(companyID);

            }
        } while (true);


        //eduSch ChoiceBox
        DB.selectSQL("SELECT fld_EduSch_ID from tbl_EduSchGenerate");
        do
        {
            String dataUserType = DB.getDisplayData();
            if (dataUserType.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                eduSch.add(dataUserType.trim());
                eduSchaccessBox.setItems(eduSch);
            }
        } while (true);


        //JDBC
        JDBC viewEduAMU = new JDBC();
        viewEduAMU.ViewEducationplanTSQL();


        try {
            preparedStatement = con.prepareStatement(viewEduAMU.ViewEducationplanTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                oblist.add(new EducationPlanConstractor(
                        resultSet.getString("fld_PlanID"),
                        resultSet.getString("fld_EducationAMU"),
                        resultSet.getString("fld_EmployeeMobile"),
                        resultSet.getString("fld_CompanyID"),
                        resultSet.getString("fld_Information"),
                        resultSet.getString("fld_EduSch_ID")));

                    }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_PlanID.setCellValueFactory(new PropertyValueFactory<>("PlanID"));
        col_EducationAMU.setCellValueFactory(new PropertyValueFactory<>("EducationAMU"));
        col_EmployeeMobile.setCellValueFactory(new PropertyValueFactory<>("EmployeeMobile"));
        col_CompanyID.setCellValueFactory(new PropertyValueFactory<>("CompanyID"));
        col_Information.setCellValueFactory(new PropertyValueFactory<>("Information"));
        col_EduSch_ID.setCellValueFactory(new PropertyValueFactory<>("EduSch_ID"));
        tableEduPlan.setItems(oblist);
    }

    /**
     * Remove an educations Plan.
     */
    @FXML
    public void removeEduPlan(){

        //JBDC to Delete an education plan
        JDBC deleteEduPlan = new JDBC();
        deleteEduPlan.DeleteEducationplanTSQL();

        //Create an object for selection cells...
        TablePosition pos = tableEduPlan.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        EducationPlanConstractor item = tableEduPlan.getItems().get(row);
        String getPlanIDField = item.getPlanID();

        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        System.out.println(data);

        try {
            preparedStatement = con.prepareStatement(deleteEduPlan.DeleteEducationplanTSQL());
            preparedStatement.setString(1,getPlanIDField);
            //We use executeUpdate() instead of executeQuery() because we don't expect any return .
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Delete Done" );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" );
        }
    }

    /**
     * Create a new educations Plan.
     */
    @FXML
    public void addEduPlan(ActionEvent event) throws Exception {

        //Create an object from JBDC class
        JDBC createEduPlan = new JDBC();
        createEduPlan.CreateEducationplanTSQL();

        //Get the Company ID value from Drop menu
        String getID = planID_TextField.getText().trim();
        String getAMU = eduAMUaccessBox.getValue();
        String getEmp = empMobileAccessBox.getValue();
        String getCom = companyIDaccessBox.getValue();
        String getInformation = information_TextField.getText().trim();
        String getEduSch = eduSchaccessBox.getValue();

        if (getID.equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill empty field");

        } else {
            DB.selectSQL("SELECT fld_PlanID from tbl_EducationPlans where fld_PlanID = '" + getID + "'");
            String un = DB.getData();

            if (un.equals(getID.toLowerCase())){
                JOptionPane.showMessageDialog(null, "ID already exist");
            }
            else{
                try {
                    //Start the JDBC
                    preparedStatement = con.prepareStatement(createEduPlan.CreateEducationplanTSQL());
                    preparedStatement.setInt(1, Integer.parseInt(getID));
                    preparedStatement.setInt(2, Integer.parseInt(getAMU));
                    preparedStatement.setInt(3, Integer.parseInt(getEmp));
                    preparedStatement.setInt(4, Integer.parseInt(getCom));
                    preparedStatement.setString(5,getInformation);
                    preparedStatement.setInt(6,Integer.parseInt(getEduSch));
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
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/FXML/main.fxml"));
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
        XSSFSheet sheet = wb.createSheet("Education Plan");
        XSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("PlanID");
        header.createCell(1).setCellValue("EducationAMU");
        header.createCell(2).setCellValue("EmployeeMobile");
        header.createCell(3).setCellValue("CompanyID");
        header.createCell(4).setCellValue("Information");
        header.createCell(5).setCellValue("EduSch_ID");

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.setZoom(125);

        int index = 1;

        //JDBC to view the education AMU
        JDBC viewEduPlan = new JDBC();
        viewEduPlan.ViewEducationplanTSQL();

        try {
            preparedStatement = con.prepareStatement(viewEduPlan.ViewEducationplanTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(resultSet.getString("fld_PlanID"));
                row.createCell(1).setCellValue(resultSet.getString("fld_EducationAMU"));
                row.createCell(2).setCellValue(resultSet.getString("fld_EmployeeMobile"));
                row.createCell(3).setCellValue(resultSet.getString("fld_CompanyID"));
                row.createCell(4).setCellValue(resultSet.getString("fld_Information"));
                row.createCell(5).setCellValue(resultSet.getString("fld_EduSch_ID"));
                index++;

            }

            FileOutputStream fileout = new FileOutputStream("EducationPlan.xlsx");
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