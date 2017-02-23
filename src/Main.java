import controllers.Display2DCtrl;
import controllers.DisplayDataCtrl;
import controllers.MainCtrl;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
/*        Parent root = FXMLLoader.load(getClass().getResource("fxml/raspberry.fxml"));*/

        primaryStage.setTitle("Hello World");
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("INVITEE");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/menu.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load(),primaryStage.getWidth(),primaryStage.getHeight()));
        primaryStage.show();

        MainCtrl controller =
                fxmlLoader.<MainCtrl>getController();
        controller.setPrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
