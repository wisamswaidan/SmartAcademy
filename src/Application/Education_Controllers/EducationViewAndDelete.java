package Application.Education_Controllers;


import Domain.EducationViewTable;
import Foundation.DB;
import Technical.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class EducationViewAndDelete implements  Initializable{

    Connection con = DB.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Button removeBut;
    @FXML
    private Button editBut;


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
    List<String> mainPlaylists = new ArrayList<String>();


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
                        resultSet.getString("fld_Information"),
                        resultSet.getString("fld_Type")));

                        mainPlaylists.add(resultSet.getString("fld_AMU"));
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
        //table.setItems (FXCollections.observableArrayList(oblist));

    }





    @FXML
    public void removeEdu(){

        //Create an object for selection cells...
        TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        EducationViewTable item = table.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        //System.out.println(data);

        //JBDC
        JDBC deleteEdu = new JDBC();
        deleteEdu.DeleteEducationTSQL();

        //Check Method if the user select the AMU column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0 ; i < mainPlaylists.size() ; i++){

            String amuSearchMatch = mainPlaylists.get(i);
            if (data.equals(amuSearchMatch)){
                //System.out.println("match found " + amuSearchMatch);
                resultMatch = amuSearchMatch;
                matchBoolena = true;

                if(matchBoolena == true){
                    try {
                        preparedStatement = con.prepareStatement(deleteEdu.DeleteEducationTSQL());
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





    @FXML
    private TextField amu_TextField;

    @FXML
    private TextField title_TextField;

    @FXML
    private TextField provider_TextField;

    @FXML
    private TextField number_of_Days_TextField;

    @FXML
    private TextField information_TextField;

    @FXML
    private TextField type_TextField;


    @FXML
    public void selectToEditEdu(){

        //Create an object from JBDC class
        JDBC edit_EduJDBC = new JDBC();
        edit_EduJDBC.EditEducationTSQL();

        //Create an object for selection cells...
        TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the table view type:
        EducationViewTable item = table.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        //System.out.println(data);

        //Check Method if the user select the AMU column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0 ; i < mainPlaylists.size() ; i++){

            String amuSearchMatch = mainPlaylists.get(i);
            if (data.equals(amuSearchMatch)){

                resultMatch = amuSearchMatch;
                System.out.println(resultMatch);

                matchBoolena = true;

                if(matchBoolena == true){

                    try {
                        //Start the JDBC
                        preparedStatement = con.prepareStatement(edit_EduJDBC.EditEducationTSQL());
                        preparedStatement.setInt(1,Integer.parseInt(resultMatch));
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {
                            String table_amu = rs.getString("fld_AMU");
                            amu_TextField.setText(table_amu);
                            amu_TextField.setDisable(true);

                            String table_title = rs.getString("fld_Title");
                            title_TextField.setText(table_title);

                            String table_provider = rs.getString("fld_Provider");
                            provider_TextField.setText(table_provider);

                            String table_numberOfdays = rs.getString("fld_NumOfDays");
                            number_of_Days_TextField.setText(table_numberOfdays);

                            String table_Information = rs.getString("fld_Information");
                            information_TextField.setText(table_Information);

                            String table_Type = rs.getString("fld_Type");
                            type_TextField.setText(table_Type);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    /*
                    DB.selectSQL("SELECT * from tbl_Educations where fld_AMU ='" + resultMatch + "' ");
                    do
                    {
                        String datas = DB.getDisplayData();
                        if (datas.equals(DB.NOMOREDATA))
                        {break;}
                        else System.out.println(datas);
                    } while (true);

                     */
                }
            }
        }

        if(matchBoolena == false){
            JOptionPane.showMessageDialog(null, "Please choose AMU column " );
        }
    }




    @FXML
    public void editEdu(){

        String amu = amu_TextField.getText().trim();
        String title = title_TextField.getText().trim();
        String provider = provider_TextField.getText().trim();
        String number_of_Days = number_of_Days_TextField.getText().trim();
        String information = information_TextField.getText().trim();
        String type = type_TextField.getText().trim();

        //Create an object from JBDC class
        JDBC update_Edu = new JDBC();
        update_Edu.UpdateEducationTSQL();


        try {
            //Start the JDBC
            preparedStatement = con.prepareStatement(update_Edu.UpdateEducationTSQL());

            preparedStatement.setString(1,title);
            preparedStatement.setString(2,information);
            preparedStatement.setInt(3,Integer.parseInt(number_of_Days));
            preparedStatement.setString(4,provider);
            preparedStatement.setString(5,type);
            preparedStatement.setInt(6,Integer.parseInt(amu));

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Edit Done" );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Wrong with Editing " );
            e.printStackTrace();
        }



    }

}
