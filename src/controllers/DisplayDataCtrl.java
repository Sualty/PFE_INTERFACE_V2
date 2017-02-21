package controllers;

import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tools.DataFileReader;

public class DisplayDataCtrl extends VBox {
    @FXML private TextField textField;
    @FXML private LineChart<Number,Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private RadioButton rbRoll;
    @FXML private RadioButton rbAccel;
    @FXML private RadioButton rbPos;
    private DataFileReader fileReader;
    private String data[][];
    private String[] currentVars;

    @FXML
    public void initialize() {
        yAxis.setLabel("variable");
        xAxis.setLabel("time");
        lineChart.setTitle("Data Display");
        currentVars = new String[3];
    }

    public void initData (String path_file) throws IOException {
        fileReader = new DataFileReader(path_file);
        data = fileReader.getData();
    }

    public void insertLineChartData () {
        if (currentVars.length == 3) {
            lineChart.getData().removeAll(lineChart.getData());
            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();
            XYChart.Series series3 = new XYChart.Series();
            series1.setName(currentVars[0]);
            series2.setName(currentVars[1]);
            series3.setName(currentVars[2]);
            switch (currentVars[0]) {
                case "Roll":
                    for (int i=1; i<10; i++) {
                        series1.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][1])));
                        series2.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][2])));
                        series3.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][3])));
                    }
                    break;
                case "AccelX":
                    for (int i=1; i<10; i++) {
                        series1.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][4])));
                        series2.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][5])));
                        series3.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][6])));
                    }
                    break;
                case "PosX":
                    for (int i=1; i<10; i++) {
                        series1.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][7])));
                        series2.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][8])));
                        series3.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][9])));
                    }
                    break;
            }
            lineChart.getData().addAll(series1, series2, series3);
        }
    }

/*    public Scene getDisplayDataScene() throws IOException {
        Scene scene  = new Scene(lineChart,800,600);

        yAxis.setLabel("variable");
        xAxis.setLabel("time");
        lineChart.setTitle("Data Display");
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        series1.setName("roll");
        series2.setName("pitch");
        series3.setName("yaw");

        String[][] data = fileReader.getData();
        for (int i=1; i<10; i++) {
            series1.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][4])));
            series2.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][5])));
            series3.getData().add(new XYChart.Data(Float.parseFloat(data[i][0]),Float.parseFloat(data[i][6])));
        }
        lineChart.getData().addAll(series1, series2, series3);

        return scene;
    }*/

    /* Switch current vars to Roll, Pitch, Yaw */
    @FXML
    protected void switchToRoll() {
        rbAccel.setSelected(false);
        rbPos.setSelected(false);
        rbRoll.setSelected(true);
        rbRoll.requestFocus();
        currentVars[0] = "Roll";
        currentVars[1] = "Pitch";
        currentVars[2] = "Yaw";
        insertLineChartData();
    }

    /* Switch current vars to AccelX, AccelY, AccelZ */
    @FXML
    protected void switchToAccel() {
        rbPos.setSelected(false);
        rbRoll.setSelected(false);
        rbAccel.setSelected(true);
        rbAccel.requestFocus();
        currentVars[0] = "AccelX";
        currentVars[1] = "AccelY";
        currentVars[2] = "AccelZ";
        insertLineChartData();
    }

    /* Switch current vars to PosX, PosY, PosZ */
    @FXML
    protected void switchToPos() {
        rbAccel.setSelected(false);
        rbRoll.setSelected(false);
        rbPos.setSelected(true);
        rbPos.requestFocus();
        currentVars[0] = "PosX";
        currentVars[1] = "PosY";
        currentVars[2] = "PosZ";
        insertLineChartData();
    }
}