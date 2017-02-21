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
        displayData(primaryStage);
    }

    public void displayData(Stage primaryStage) throws IOException {
        /* Change current scene to display_data.fxml */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/display_data.fxml"));
        Pane pane = (Pane) loader.load();
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(pane,primaryStage.getWidth(),primaryStage.getHeight()));
        primaryStage.show();
        /* Input the data into the line chart */
        DisplayDataCtrl controller =
                loader.<DisplayDataCtrl>getController();
        controller.initFileReader("res/data");
        controller.initLineChartData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
