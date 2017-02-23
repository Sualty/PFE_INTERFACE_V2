package controllers;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import tools.Axes;
import tools.DataFileReader;

import java.io.IOException;

/**
 * Created by user on 22/02/17.
 */
public class Display2DCtrl {
    @FXML
    private Group group;
    private DataFileReader fileReader;
    private Double dataA[][];
    private Double dataB[][];
    private final int centerX = 275;
    private final int centerY = 250;
    private final int coefPos = 5;
    private final double duration = 5.0;
    @FXML private RadioButton rbXY;
    @FXML private RadioButton rbXZ;
    @FXML private RadioButton rbYZ;
    private Circle circle;
    private Axes axes;

    public Display2DCtrl() {
    }

    @FXML
    public void initialize() {
        circle = new Circle(centerX, centerY, 5);
        circle.setFill(Color.DARKRED);
        axes = new Axes(
                550, 500,
                -1, 1, 1,
                -1, 1, 1
        );
    }

    public void initDataA (String path_file) throws IOException {
        fileReader = new DataFileReader(path_file);
        dataA = fileReader.getData();
    }

    public void initDataB (String path_file) throws IOException {
        fileReader = new DataFileReader(path_file);
        dataB = fileReader.getData();
    }

    private void drawSwordPath(String flagAxes, Double data[][]) {
        if (!group.getChildren().isEmpty()) {
            group.getChildren().removeAll(group.getChildren());
        }
        group.getChildren().add(axes);
        final Path path = new Path();
        switch (flagAxes) {
            case "XY":
                for (int i = 1; i < dataA.length-1; i=i+1) {
                    if (i == 1) {
                        path.getElements().add(new MoveTo(data[i][7]*coefPos + centerX, data[i][8]*coefPos + centerY));
                    } else {
                        path.getElements().add(new LineTo(data[i][7]*coefPos + centerX,data[i][8]*coefPos + centerY));
                    }
                }
                break;
            case "XZ":
                for (int i = 1; i < data.length-1; i=i+1) {
                    if (i == 1) {
                        path.getElements().add(new MoveTo(data[i][7]*coefPos + centerX, data[i][9]*coefPos + centerY));
                    } else {
                        path.getElements().add(new LineTo(data[i][7]*coefPos + centerX,data[i][9]*coefPos + centerY));
                    }
                }
                break;
            case "YZ":
                for (int i = 1; i < data.length-1; i=i+1) {
                    if (i == 1) {
                        path.getElements().add(new MoveTo(data[i][8]*coefPos + centerX, data[i][9]*coefPos + centerY));
                    } else {
                        path.getElements().add(new LineTo(data[i][8]*coefPos + centerX,data[i][9]*coefPos + centerY));
                    }
                }
                break;
            default:
                break;
        }

        path.setOpacity(0.1);
        group.getChildren().add(path);
        group.getChildren().add(circle);
        final PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(duration));
        pathTransition.setDelay(Duration.seconds(1.0));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition
                .setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.play();
    }

    /* Display movement on axes XY */
    @FXML
    protected void switchToXY() {
        rbYZ.setSelected(false);
        rbXZ.setSelected(false);
        rbXY.setSelected(true);
        rbXY.requestFocus();
        drawSwordPath("XY", dataA);
    }

    /* Display movement on axes XZ */
    @FXML
    protected void switchToXZ() {
        rbXY.setSelected(false);
        rbYZ.setSelected(false);
        rbXZ.setSelected(true);
        rbXZ.requestFocus();
        drawSwordPath("XZ", dataA);
    }

    /* Display movement on axes YZ */
    @FXML
    protected void switchToYZ() {
        rbXY.setSelected(false);
        rbXZ.setSelected(false);
        rbYZ.setSelected(true);
        rbYZ.requestFocus();
        drawSwordPath("YZ", dataA);
    }





}
