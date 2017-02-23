import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

// Java 8 code
public class Test extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        final Group group = new Group();
        final Scene scene = new Scene(group, 600, 400, Color.GHOSTWHITE);
        stage.setScene(scene);
        stage.setTitle("JavaFX 2 Animations");
        stage.show();
        final Circle circle = new Circle(20, 20, 15);
        circle.setFill(Color.DARKRED);

        final Path path = new Path();
        path.getElements().add(new MoveTo(20, 20));
        path.getElements().add(new LineTo(200, 10));
        path.getElements().add(new LineTo(100, 30));
        path.getElements().add(new LineTo(400, 240));
        path.getElements().add(new LineTo(350, 310));
        path.getElements().add(new LineTo(25, 30));
/*        path.getElements().add(new CubicCurveTo(100, 100, 80, 20, 200, 120));
        path.getElements().add(new CubicCurveTo(200, 1120, 110, 240, 380, 240));*/
        path.setOpacity(0.5);

        group.getChildren().add(path);
        group.getChildren().add(circle);
        final PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(10.0));
        pathTransition.setDelay(Duration.seconds(.8));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition
                .setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}