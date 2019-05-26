import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DB_TSQL extends Application {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection con = DB.connect();
        Text headerWelcome = new Text("Welcome to SmartAcademy");
        Text headerWelcome2 = new Text("The Future Workforce");

        TextField user = new TextField();
        user.setPromptText("Your username");

        PasswordField pass = new PasswordField();
        pass.setPromptText("Your password");

        Button button1 = new Button("Login");

        button1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent LoginEvent) {

                String userName = user.getText().trim();
                String password = pass.getText().trim();

                //Query
                String sql = "SELECT * from tblUser where fldUserName = ? and fldPassword = ?";

                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1,userName);
                    preparedStatement.setString(2,password);
                    resultSet = preparedStatement.executeQuery();


                    if (!resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Username or password is wrong" );
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "All good and working - Now open a new scene");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }

    });
        //Creating a Grid Pane
        GridPane gridPane = new GridPane();
        //Setting size for the pane
        gridPane.setMinSize(500, 500);
        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);
        //Arranging all the nodes in the grid
        gridPane.add(headerWelcome, 0, 0);
        GridPane.setHalignment(headerWelcome, HPos.CENTER);

        gridPane.add(headerWelcome2, 0, 1);
        GridPane.setHalignment(headerWelcome2, HPos.CENTER);

        gridPane.add(user, 0, 2);
        user.setFocusTraversable(false);

        gridPane.add(pass, 0, 3);
        pass.setFocusTraversable(false);

        gridPane.add(button1, 0, 4);
        button1.setMaxSize(200, 200);
        GridPane.setHalignment(button1, HPos.CENTER);

        // Creating a scene object
        Scene scene = new Scene(gridPane);

        // Setting title to the Stage
        primaryStage.setTitle("SmartAcademy");
        // Adding scene to the stage
        primaryStage.setScene(scene);
        //Displaying the contents of the stage
        primaryStage.show();

        }

}
