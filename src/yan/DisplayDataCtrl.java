package yan;

import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tools.DataFileReader;

public class DisplayDataCtrl extends VBox {
    @FXML private TextField textField;
    private LineChart<Number,Number> lineChart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private DataFileReader fileReader;
    private String path_file;

    public DisplayDataCtrl(String path_file) {
        this.path_file = path_file;
        fileReader = new DataFileReader(this.path_file);
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
    }

    public Scene getDisplayDataScene() throws IOException {
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
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return textField.textProperty();
    }

    @FXML
    protected void show() {
        System.out.println("The button was clicked!");
    }
}