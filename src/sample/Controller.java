package sample;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.*;

public class Controller {
    @FXML
    private TextField timer_textfield;
    @FXML
    private Label timer_and_result_label;
    @FXML
    private Button launch_acq_button;

    private boolean isLaunchingAcq;

    @FXML
    public void initialize() {
        System.out.println("initializing controller");

        isLaunchingAcq = false;

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

//TODO launch connection at 1 second
    public void launchAcq(ActionEvent actionEvent) {
        if(!isLaunchingAcq) {
            launch_acq_button.getStyleClass().add("busy");

            isLaunchingAcq = true;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int time = 0;
                    if (timer_textfield.getText().length() > 0)
                        time = Integer.parseInt(timer_textfield.getText());

                    for (int i = time; i >= 0; i--) {
                        final int j = i;
                        CallableVoice callableVoice = new CallableVoice(j);
                        CallableLabel callableLabel = new CallableLabel(j);

                        FutureTask<String> futureTask1 = new FutureTask<String>(callableVoice);
                        FutureTask<String> futureTask2 = new FutureTask<String>(callableLabel);

                        ExecutorService executor = Executors.newFixedThreadPool(2);

                        executor.execute(futureTask1);
                        executor.execute(futureTask2);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            launch_acq_button.getStyleClass().removeAll("busy");
                            launch_acq_button.getStyleClass().add("button");
                        }
                    });
                    isLaunchingAcq = false;
                }
            });
            thread.start();
        }
    }

    public void sayNumber(int i) {
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");

        voice.allocate();

        voice.speak(i+"");
    }


    private class CallableVoice implements Callable<String> {
        int j;
        public CallableVoice(int j) {
            this.j = j;
        }
        @Override
        public String call() throws Exception {
            sayNumber(j);
            //return the thread name executing this callable task
            return Thread.currentThread().getName();
        }
    }

    private class CallableLabel implements Callable<String> {
        int j;
        public CallableLabel(int j) {
            this.j = j;
        }
        @Override
        public String call() throws Exception {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    timer_and_result_label.setText(j+"");
                }
            });
            //return the thread name executing this callable task
            return Thread.currentThread().getName();
        }
    }
}
