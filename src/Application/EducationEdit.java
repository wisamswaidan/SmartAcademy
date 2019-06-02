package Application;

import Foundation.DB;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EducationEdit {
    PreparedStatement preparedStatement = null;

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
    public void startEdit(){

     /*
    //Text field read the javafx fields .
    String amu = amu_TextField.setText();
    String title = title_TextField.setText();
    String provider = provider_TextField.setText();
    String number_of_Days = number_of_Days_TextField.setText();
    String information = information_TextField.setText();
    String type = type_TextField.setText();
    */

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
