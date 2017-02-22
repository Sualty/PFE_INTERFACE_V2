import controllers.DisplayDataCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_Yan extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
/*        displayData(primaryStage);*/
        display2D(primaryStage);
    }

    public void displayData(Stage primaryStage) throws IOException {

        /* Change current scene to display_data.fxml */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/display_data.fxml"));
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(fxmlLoader.load(),primaryStage.getWidth(),primaryStage.getHeight()));
        primaryStage.show();

        /* Get the data from data file */
        DisplayDataCtrl controller =
                fxmlLoader.<DisplayDataCtrl>getController();
        controller.initData("res/data");
    }

    public void display2D(Stage primaryStage) throws IOException {
        /* Change current scene to display_data.fxml */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/display2D.fxml"));
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(fxmlLoader.load(),primaryStage.getWidth(),primaryStage.getHeight()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
