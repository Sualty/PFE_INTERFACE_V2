package yan;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        displayData(primaryStage);
    }

    public void displayData(Stage primaryStage) throws IOException {
        DisplayDataCtrl displayData = new DisplayDataCtrl("res/data");
        primaryStage.setScene(displayData.getDisplayDataScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
