package Application;

import Domain.EducationSchConstractor;
import Foundation.DB;
import Technical.JDBC;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;




public class EducationSchController  implements Initializable {

    //Start the connection to DB.
    Connection con = DB.connect();
    //Create preparedStatement Object
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    Button addDateBut ,schEduBut  , deleteBut, backBut;

    @FXML
    private ChoiceBox<String> amuBox;
    @FXML
    private ChoiceBox<String> idBox;


    @FXML
    private TableView<EducationSchConstractor> schEduTable;

    @FXML
    private TableColumn <EducationSchConstractor, String> col_id;
    @FXML
    private TableColumn <EducationSchConstractor, String> col_amu;
    @FXML
    private TableColumn <EducationSchConstractor, String> col_date;


    //Create ObservableList to read user access level from database and use it for Drop menu
    ObservableList<String> amuList = FXCollections.observableArrayList();
    ObservableList<String> idList = FXCollections.observableArrayList();

    //
    ObservableList<EducationSchConstractor> oblist = FXCollections.observableArrayList();
    List<String> eduSchIDSearch = new ArrayList<String>();

    //
    ObservableList<LocalDate> selectedDates = FXCollections.observableArrayList();
    ListView<LocalDate>dateList = new ListView<>(selectedDates);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DB.selectSQL("select fld_AMU from tbl_Educations");
        do
        {
            String companyID = DB.getDisplayData();
            if (companyID.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                amuList.add(companyID.trim());
                amuBox.setItems(amuList);

            }
        } while (true);


        DB.selectSQL("select fld_EduSch_ID from tbl_EduSchGenerate");
        do
        {
            String companyID = DB.getDisplayData();
            if (companyID.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                idList.add(companyID.trim());
                idBox.setItems(idList);

            }
        } while (true);



        //JDBC
        JDBC viewEduSch = new JDBC();
        viewEduSch.ViewEducationSchTSQL();

        try {

            preparedStatement = con.prepareStatement(viewEduSch.ViewEducationSchTSQL());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                oblist.add(new EducationSchConstractor(
                        resultSet.getString("fld_EduSch_ID"),
                        resultSet.getString("AMU"),
                        resultSet.getString("fld_Date")));

                // Add the value we need to check for a match with to the list
                eduSchIDSearch.add(resultSet.getString("fld_EduSch_ID"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_amu.setCellValueFactory(new PropertyValueFactory<>("AMU"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("Date"));

        schEduTable.setItems(oblist);
    }

    //Method to Remove the company
    @FXML
    public void removeEduSch(){

        //JBDC
        JDBC deleteEduSch = new JDBC();
        deleteEduSch.DeleteEducationSchSQL();

        //Create an object for selection cells...
        TablePosition pos = schEduTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        //Item here is the educations_table view type:
        EducationSchConstractor item = schEduTable.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        // Gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        //System.out.println(data);


        //Check Method if the user select the AMU column to start the Delete process .
        boolean matchBoolena = false;
        String resultMatch = "";

        for(int i = 0; i < eduSchIDSearch.size() ; i++){

            String amuSearchMatch = eduSchIDSearch.get(i);
            if (data.equals(amuSearchMatch)){
                resultMatch = amuSearchMatch;
                matchBoolena = true;

                if(matchBoolena == true){
                    try {
                        preparedStatement = con.prepareStatement(deleteEduSch.DeleteEducationSchSQL());
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
            JOptionPane.showMessageDialog(null, "Please choose ID column " ); }



    }

    @FXML
    public void launchSwing() {
        SwingUtilities.invokeLater(() -> {
            JFrame         frame   = new JFrame("Calender");
            final JFXPanel fxPanel = new JFXPanel();
            frame.add(fxPanel);
            frame.setVisible(true);
            frame.setBounds(200,200,500,600);
            Platform.runLater(() -> fxPanel.setScene(createScene()));

        });
    }

    @FXML
    public Scene createScene() {
        Button addButton     = new Button("+");
        Button removeButton  = new Button("-");


        String                    pattern       = "yyyy-MM-dd";
        DateTimeFormatter         dateFormatter = DateTimeFormatter.ofPattern(pattern);
        DatePicker                datePicker    = new DatePicker();
        datePicker.setShowWeekNumbers(true);
        datePicker.setOnAction(event -> System.out.println("Selected date: " + datePicker.getValue()));
        datePicker.setPromptText(pattern);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date == null) ? "" : dateFormatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return ((string == null) || string.isEmpty()) ? null : LocalDate.parse(string, dateFormatter);
            }
        });
        datePicker.setOnAction(event -> selectedDates.add(datePicker.getValue()));
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean alreadySelected = selectedDates.contains(item);
                        setDisable(alreadySelected);
                        setStyle(alreadySelected ? "-fx-background-color: #09a30f;" : "");
                    }
                };
            }
        });

        // TODO: Hide text field of the date picker combo. Show dropdown directly on clicking "+" button.
        // TODO: Keep dropdown of the date picker combo open until intentionally clicking some other where.

        dateList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                removeSelectedDates(selectedDates, dateList);
            }
        });

        dateList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeButton.disableProperty().bind(dateList.getSelectionModel().selectedItemProperty().isNull());
        addButton.setOnAction(event -> {
            Popup popup = new Popup();
            popup.getContent().add(datePicker);
            popup.setAutoHide(true);
            Window window = addButton.getScene().getWindow();
            Bounds bounds = addButton.localToScene(addButton.getBoundsInLocal());
            double x      = window.getX() + bounds.getMinX();
            double y      = window.getY() + bounds.getMinY() + bounds.getHeight() + 5;
            popup.show(addButton, x, y);
            datePicker.show();
        });
        removeButton.setOnAction(event -> removeSelectedDates(selectedDates, dateList));

        HBox buttons = new HBox(addButton, removeButton);
        return new Scene(new VBox(buttons, dateList));

    }

    private static boolean removeSelectedDates(ObservableList<LocalDate> selectedDates, ListView<LocalDate> dateList) {
        return selectedDates.removeAll(dateList.getSelectionModel().getSelectedItems());
    }

    @FXML
    public void addEduSch(){

        String getAMU = amuBox.getValue();
        String getID = idBox.getValue();

        //Create an object from JBDC class
        JDBC createEduSch = new JDBC();
        createEduSch.CreateEducationSchTSQL();


        for(int i = 0; i < selectedDates.size(); i++){
            String getSelectedDate = String.valueOf(selectedDates.get(i));
            System.out.println(getSelectedDate);

            try {
                //Start the JDBC
                preparedStatement = con.prepareStatement(createEduSch.CreateEducationSchTSQL());
                preparedStatement.setInt(1,Integer.parseInt(getID));
                preparedStatement.setInt(2,Integer.parseInt(getAMU));
                preparedStatement.setString(3,getSelectedDate);
                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Wrong with creating" );
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(null, "Create Done" );
    }


    public void backButton(ActionEvent event) throws Exception {
        Parent showPage = FXMLLoader.load(getClass().getResource("/UI/main.fxml"));
        Scene showScene = new Scene(showPage);
        Stage showApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showApp.setScene(showScene);
        showApp.show();

    }

}
