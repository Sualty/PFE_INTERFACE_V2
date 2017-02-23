import controllers.Display2DCtrl;
import controllers.DisplayDataCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main_Yan extends Application {

    private final String path_dataA = "res/dataA";
    private final String path_dataB = "res/dataB";

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
        controller.initData(path_dataA);
    }

    public void display2D(Stage primaryStage) throws IOException {
        /* Change current scene to display2D.fxml */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/display2D.fxml"));
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(fxmlLoader.load(),primaryStage.getWidth(),primaryStage.getHeight()));
        primaryStage.show();

        /* Get the data from data file */
        Display2DCtrl controller =
                fxmlLoader.<Display2DCtrl>getController();
        controller.initDataA(path_dataA);
        controller.initDataB(path_dataB);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
