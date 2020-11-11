package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    // Adjusts the properties for the setup of the application.
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Student Database");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }

    // Runs the application.
    public static void main(String[] args) {
        launch(args);
    }
}
