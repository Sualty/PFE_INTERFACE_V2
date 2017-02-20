package sample;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

public class Controller {
    @FXML
    private TextField timer_textfield;
    @FXML
    private Label timer_and_result_label;

    @FXML
    public void initialize() {
        System.out.println("initializing controller");
        timer_textfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    timer_textfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        timer_textfield.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (timer_textfield.getText().length() >= 2) {

                        // if it's 11th character then just setText to previous
                        // one
                        timer_textfield.setText(timer_textfield.getText().substring(0,2));
                    }
                }
            }});
    }

    public void connectToRPI(ActionEvent actionEvent) {
        //TODO
    }


    public void launchAcq(ActionEvent actionEvent) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time=0;
                if(timer_textfield.getText().length()>0)
                    time = Integer.parseInt(timer_textfield.getText());

                for(int i = time;i>=0;i--) {
                    final int j = i;
                    sayNumber(j);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timer_and_result_label.setText(j+"");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void sayNumber(int i) {
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");

        voice.allocate();

        voice.speak(i+"");
    }
}
