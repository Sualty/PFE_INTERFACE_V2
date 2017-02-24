package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by user on 23/02/17.
 */
public class MainCtrl {
    @FXML
    private Button btnRaspberry;
    @FXML
    private Button btnDataDisplay;
    @FXML
    private Button btn2DDisplay;

    private Stage primaryStage;

    private final String path_dataA = "res/dataA";
    private final String path_dataB = "res/dataB";

    public MainCtrl () {
    }

    public void setPrimaryStage (Stage stage) {
        primaryStage = stage;
    }

    @FXML
    public void displayRaspberry() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/raspberry.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load(),primaryStage.getWidth(),primaryStage.getHeight()));

        RaspberryCtrl controller =
                fxmlLoader.<RaspberryCtrl>getController();
        controller.setPrimaryStage(primaryStage);
    }

    @FXML
    public void displayData() throws IOException {
        /* Change current scene to display_data.fxml */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/display_data.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load(),primaryStage.getWidth(),primaryStage.getHeight()));

        /* Get the data from data file */
        DisplayDataCtrl controller =
                fxmlLoader.<DisplayDataCtrl>getController();
        controller.setPrimaryStage(primaryStage);
        controller.initData(path_dataA);
    }

    @FXML
    public void display2D() throws IOException {
        /* Change current scene to display2D.fxml */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/display2D.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load(),primaryStage.getWidth(),primaryStage.getHeight()));

        /* Get the data from data file */
        Display2DCtrl controller =
                fxmlLoader.<Display2DCtrl>getController();
        controller.setPrimaryStage(primaryStage);
        controller.initDataA(path_dataA);
        controller.initDataB(path_dataB);
    }


}
